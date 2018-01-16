/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/22/17
Time: 8:35 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.PipeLineInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.Phase
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLine

interface IPipeLineSubject {
    val Name: String
    val Description: String
}

class CorePipeLine : PipeLine<IPipeLineSubject, IDataContext>(before, beforeFilter, beforeProcess, process, afterProcess, afterFilter, after) {
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
         * ئىجرا بولۇشتىن بۇرۇنقى سۈزۈش باسقۇچى
         */
        val beforeFilter = Phase("Before Filter")
        /**
         * ئىجرا بولۇشتىن ئاۋالقى بىر تەرەپ قىلىش باسقۇچى
         */
        val beforeProcess= Phase("Before Process")
        /**
         *ئاساسلىق بىر تەرەپ قىلىش باسقۇچى
         */
        val process = Phase("Main Process")
        /**
         * ئىجرا بولغاندىن كىيىنكى بىر تەرەپ قىلىش باسقۇچى
         */
        val afterProcess = Phase("After Process")
        /**
         * ئىجرا بولۇپ بولغاندىن كىيىنكى سۈزۈش باسقۇچى
         */
        val afterFilter = Phase("After Filter")
        /**
         * بارلىق باسقۇچ ئاخىرلاشقاندىن كىيىن
         */
        val after = Phase("After")
    }
}