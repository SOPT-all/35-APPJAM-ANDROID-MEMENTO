package org.memento.presentation.util

fun Char.isKorean(): Boolean {
    return this in '\uAC00'..'\uD7A3' || this in '\u1100'..'\u11FF' || this in '\u3130'..'\u318F'
}
