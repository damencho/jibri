/*
 * Copyright @ 2018 Atlassian Pty Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.jitsi.jibri.util

import org.jitsi.jibri.util.extensions.error
import java.nio.file.Files
import java.nio.file.Path
import java.util.logging.Logger

fun createIfDoesNotExist(path: Path, logger: Logger? = null): Boolean {
    if (!Files.exists(path)) {
        try {
            Files.createDirectories(path)
        } catch (e: Exception) {
            logger?.error("Error creating directory", e)
            return false
        }
    }
    return true
}

fun deleteRecursively(path: Path, logger: Logger? = null): Boolean {
    try {
        Files.walk(path)
            // Reverse sort makes sure we process the directories
            // after the files within them
            .sorted(Comparator.reverseOrder())
            .forEach(Files::delete)
    } catch (e: Exception) {
        logger?.error("Error recursively deleting $path", e)
        return false
    }
    return true
}
