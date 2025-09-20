package com.l2code.tmdb

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.loggerConfigInit

expect val logWriter: LogWriter
val log = Logger(
    loggerConfigInit(logWriter),
    "TMDb"
)