package com.manoj.audioparams.musicsync;

/* Copyright 2017 Eddy Xiao <bewantbe@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.content.res.Resources;
import android.media.MediaRecorder;
import android.util.Log;

import com.manoj.audioparams.R;


/**
 * Basic properties of Analyzer.
 */

public class AnalyzerParameters {
    public final int RECORDER_AGC_OFF = MediaRecorder.AudioSource.VOICE_RECOGNITION;
    public int audioSourceId = RECORDER_AGC_OFF;
    public int sampleRate = 16000;
    public int fftLen = 2048;
    public int hopLen = 1024;
    public double overlapPercent = 50;  // = (1 - hopLen/fftLen) * 100%
    public String wndFuncName;
    int nFFTAverage = 2;
    boolean isAWeighting = false;
    final int BYTE_OF_SAMPLE = 2;
    final double SAMPLE_VALUE_MAX = 32767.0;   // Maximum signal value
    public double spectrogramDuration = 4.0;

    double[] micGainDB = null;  // should have fftLen/2+1 elements, i.e. include DC.
    String calibName = null;

    public AnalyzerParameters(Resources res) {
        getAudioSourceNameFromIdPrepare(res);
    }

    String[] audioSourceNames;
    int[] audioSourceIDs;
    private void getAudioSourceNameFromIdPrepare(Resources res) {
        audioSourceNames   = res.getStringArray(R.array.audio_source);
        String[] sasid = res.getStringArray(R.array.audio_source_id);
        audioSourceIDs = new int[audioSourceNames.length];
        for (int i = 0; i < audioSourceNames.length; i++) {
            audioSourceIDs[i] = Integer.parseInt(sasid[i]);
        }
    }

    // Get audio source name from its ID
    // Tell me if there is better way to do it.
    String getAudioSourceNameFromId(int id) {
        for (int i = 0; i < audioSourceNames.length; i++) {
            if (audioSourceIDs[i] == id) {
                return audioSourceNames[i];
            }
        }
        Log.i("AnalyzerParameters", "getAudioSourceName(): non-standard entry.");
        return ((Integer)(id)).toString();
    }

    String getAudioSourceName() {
        return getAudioSourceNameFromId(audioSourceId);
    }
}

