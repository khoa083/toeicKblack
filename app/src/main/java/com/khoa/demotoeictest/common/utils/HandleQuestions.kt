package com.khoa.demotoeictest.common.utils

import androidx.lifecycle.LiveData
import com.khoa.demotoeictest.data.ScoreToeic
import com.khoa.demotoeictest.model.Parts
import com.khoa.demotoeictest.model.PartsData
import com.khoa.demotoeictest.room.listparts.ListPartsDao

class HandleQuestions {
    companion object {

        fun calculatorQues(arr: Array<Array<Int>>, listData: ArrayList<PartsData>): Array<Array<Int>> {
            listData.forEachIndexed { idx, value ->
                for (childIdx in 0 until 5) {
                    if (value.smallQues1 != "" && childIdx==0) {
                        arr[idx][0] = 3
                    } else if (value.smallQues2 != "" && childIdx==1) {
                        arr[idx][1] = 3
                    } else if (value.smallQues3 != "" && childIdx==2) {
                        arr[idx][2] = 3
                    } else if (value.smallQues4 != "" && childIdx==3) {
                        arr[idx][3] = 3
                    } else if (value.smallQues5 != "" && childIdx==4) {
                        arr[idx][4] = 3
                    }
                }
            }
            return arr
        }

//      Convert to @d Array
        fun flatten2DArray(array: Array<Array<Int>>): List<Int> {
            return array.flatMap { it.toList() }
        }

//      Merger Arrays
        // TODO: INPUT: [2, 1, 0, 0, 0, 1, 0 ,0 ,0 ,0] and [3, 3, 3, 0, 0, 3, 3 ,0 ,0 ,0]
        // TODO: OUTPUT: [2, 1, 3, 0, 0, 1, 3 ,0 ,0 ,0]
        fun mergeArrays(arr1: ArrayList<Int>, arr2: ArrayList<Int>): ArrayList<Int>
        = arr1.zip(arr2) { a,b ->
            when {
                a != 0 -> a
                b != 0 -> b
                else -> 0
            }
        } as ArrayList<Int>

        fun calculatorScore(originResult: ArrayList<Int>?, type: String?): Int {
            var count = 0
            var score = 0
            when(type) {
                "listen"->{
                    for (i in 0..269) {
                        if((originResult?.get(i) ?: 0) == 1) {
                            ++count
                        }
                    }
                    score = ScoreToeic.listeningMap[count]?:0
                }
                "read"->{
                    for (i in 270..514) {
                        if((originResult?.get(i) ?: 0) ==1) {
                            ++count
                        }
                    }
                    score = ScoreToeic.readingMap[count]?:0
                }
            }
            return score
        }
    }

}