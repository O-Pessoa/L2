package com.l2code.tmdb

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.LogcatWriter

actual val logWriter: LogWriter = LogcatWriter()