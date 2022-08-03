# Plots
import matplotlib.pyplot as plt
from pylab import plot, show, figure, imshow
plt.rcParams['figure.figsize'] = (15, 6)

import numpy
import essentia.standard as es

import statistics
from statistics import mode

import math
# Plots
import matplotlib.pyplot as plt
from pylab import plot, show, figure, imshow
plt.rcParams['figure.figsize'] = (15, 6)

import numpy
import essentia.standard as es

import statistics
from statistics import mode

import math

import json

def most_common(List):
    return(mode(List))
    
def load_json(name_file):
    data = None
    with open(name_file, 'r') as fp:
        data = json.load(fp)
    return data


    
def frequency_to_note(frequency):
    # define constants that control the algorithm
    NOTES = ['C', 'C#', 'D', 'D#', 'E', 'F', 'F#', 'G', 'G#', 'A', 'A#', 'B'] # these are the 12 notes in each octave
    OCTAVE_MULTIPLIER = 2 # going up an octave multiplies by 2
    KNOWN_NOTE_NAME, KNOWN_NOTE_OCTAVE, KNOWN_NOTE_FREQUENCY = ('A', 4, 440) # A4 = 440 Hz

    # calculate the distance to the known note
    # since notes are spread evenly, going up a note will multiply by a constant
    # so we can use log to know how many times a frequency was multiplied to get from the known note to our note
    # this will give a positive integer value for notes higher than the known note, and a negative value for notes lower than it (and zero for the same note)
    note_multiplier = OCTAVE_MULTIPLIER**(1/len(NOTES))
    frequency_relative_to_known_note = frequency / KNOWN_NOTE_FREQUENCY
    distance_from_known_note = math.log(frequency_relative_to_known_note, note_multiplier)

    # round to make up for floating point inaccuracies
    distance_from_known_note = round(distance_from_known_note)

    # using the distance in notes and the octave and name of the known note,
    # we can calculate the octave and name of our note
    # NOTE: the "absolute index" doesn't have any actual meaning, since it doesn't care what its zero point is. it is just useful for calculation
    known_note_index_in_octave = NOTES.index(KNOWN_NOTE_NAME)
    known_note_absolute_index = KNOWN_NOTE_OCTAVE * len(NOTES) + known_note_index_in_octave
    note_absolute_index = known_note_absolute_index + distance_from_known_note
    note_octave, note_index_in_octave = note_absolute_index // len(NOTES), note_absolute_index % len(NOTES)
    note_name = NOTES[note_index_in_octave]
    return (note_name, note_octave)

def singscore(f1,f2,theo_f1,theo_f2):
    upper = 500
    vals = []
    for i in range(7):
        vals.append(i*upper/6)
        
    print(vals)
    score = upper - abs(f1 - theo_f1) - abs(f2 - theo_f2)
    print(score)
    if   (vals[6]-1 >= score and score >= vals[5]):
        return 'Perfect'
    elif (vals[5]-1 >= score and score >= vals[4]):
        return 'Well done'
    elif (vals[4]-1 >= score and score >= vals[3]):
        return 'Okay'
    elif (vals[3]-1 >= score and score >= vals[2]):
        return 'Try harder'
    elif (vals[2] >= score and score >= vals[1]):
        return 'Maybe next time'   
    else:
        return 'Not good'
        
audiofile = './whts.mp3'

# Load audio file.
# It is recommended to apply equal-loudness filter for PredominantPitchMelodia.
loader = es.EqloudLoader(filename=audiofile, sampleRate=44100)
audio = loader()
print("Duration of the audio sample [sec]:")
print(len(audio)/44100.0)

# Extract the pitch curve
# PitchMelodia takes the entire audio signal as input (no frame-wise processing is required).

pitch_extractor = es.PredominantPitchMelodia(frameSize=2048, hopSize=128)
pitch_values, pitch_confidence = pitch_extractor(audio)

# Pitch is estimated on frames. Compute frame time positions.
pitch_times = numpy.linspace(0.0,len(audio)/44100.0,len(pitch_values) )
'''
# Plot the estimated pitch contour and confidence over time.
f, axarr = plt.subplots(2, sharex=True)
axarr[0].plot(pitch_times, pitch_values)
axarr[0].set_title('estimated pitch [Hz]')
axarr[1].plot(pitch_times, pitch_confidence)
axarr[1].set_title('pitch confidence')
plt.show()
'''

f1 = most_common(pitch_values)
print(frequency_to_note(f1))

pitch_values = list(filter(lambda a: a != f1, pitch_values))
f2 = most_common(pitch_values)
print(frequency_to_note(f2))


vocal_ranges = ['alto','baritono','bass','mezzo_soprano','soprano','tenor']

vocal_range = vocal_ranges[4] # get from the app
interval_id = 'd5' # get from marinapp

interval_id += '.jpg'

json_pth = 'jsons/index_'+vocal_range+'.json'
intervals_dicts = load_json(json_pth)

theo_f1 = intervals_dicts[interval_id]['f1']
theo_f2 = intervals_dicts[interval_id]['f2']

print(theo_f1, theo_f2)
print(f1, f2)
print(singscore(f1, f2, theo_f1, theo_f2))
