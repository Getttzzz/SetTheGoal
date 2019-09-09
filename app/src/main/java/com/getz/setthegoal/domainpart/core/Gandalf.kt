package com.getz.setthegoal.domainpart.core

/**
 * Gandalf is a fictional character and a protagonist in Tolkien's novels
 * The Hobbit and The LOTR. He is a wizard who can transform one thing to another.
 * "Mapper" name is too boring.
 * */
interface Gandalf<in Source, out Result> {
    fun transform(source: Source): Result
}