/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/22/17
Time: 8:35 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.PipeLine

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.PipeLineInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.Phase
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLine

interface PipeLineSubject

class CorePipeLine : PipeLine<PipeLineSubject, IDataContext>(before, translationBefore, translationBeforeFilter, translation, translationAfterFilter, translationAfter, executionBefore, executionBeforeFilter, execution, executionAfterFilter, executionAfter, after) {
    override val info: PipeLineInfo = PipeLineInfo(
            "InfraStructurePipeLine",
            "Query Translate to any databases Sql Dialets and execute this pipeline",
            "Sarkar Software Technologys",
            "yeganaaa",
            1,
            "v0.1"
    )
    companion object {
        /**
         *بارلىق جەريان ئىجرا قىلىنىشتىن بۇرۇن
         */
        val before = Phase("Before")
        /**
         *تەرجىمە قىلىنىشتىن بۇرۇن
         */
        val translationBefore = Phase("Translation Before")
        /**
         *تەرجىمە قىلىنىشتىن بۇرۇنقى سۈزۈش تەييارلىقى
         */
        val translationBeforeFilter = Phase("Translation Before Filter")
        /**
         *تەرجىمە قىلىنىش باسقۇچى
         */
        val translation = Phase("Translation")
        /**
         *تەرجىمە قىلىنىپ بولغاندىن كىيىنكى سۈزۈش باسقۇچى
         */
        val translationAfterFilter = Phase("Translation After Filter")
        /**
         *تەرجىمە قىلىنىپ بولغاندىن كىيىن
         */
        val translationAfter = Phase("Translation After")
        /**
         *بۇيرۇق يۈرگۈزۈلۈشتىن بۇرۇن
         */
        val executionBefore = Phase("Execution Before")
        /**
         * بۇيرۇق يۈرگۈزۈلۈشتىن بۇرۇنقى سۈزۈش باسقۇچى
         */
        val executionBeforeFilter = Phase("Execution Before Filter")
        /**
         * بۇيرۇق يۈرگۈزۈلۈش باسقۇچى
         */
        val execution = Phase("Execution")
        /**
         * بۇيرۇق يۈرگۈزۈلگەندىن كىيىنكى سۈزۈش باسقۇچى
         */
        val executionAfterFilter = Phase("Execution After Filter")
        /**
         * بۇيرۇق يۈرگۈزۈلگەندىن كىيىن
         */
        val executionAfter = Phase("Execution After")
        /**
         * بارلىق باسقۇچ ئاخىرلاشقاندىن كىيىن
         */
        val after = Phase("After")
    }
}