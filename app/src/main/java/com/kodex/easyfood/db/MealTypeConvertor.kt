package com.kodex.easyfood.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.nio.file.attribute.FileAttribute

@TypeConverters
class MealTypeConvertor {

    @TypeConverter
    fun frontAnyToString(attribute: Any?): String{
        if (attribute == null)
            return " "
        return attribute as String

    }
    @TypeConverter
    fun fromStringToAny(attribute: String?): Any{
        if (attribute == null)
            return " "
        return attribute
    }
}