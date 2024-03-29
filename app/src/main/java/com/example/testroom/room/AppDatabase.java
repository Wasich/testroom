package com.example.testroom.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Recipe.class} ,  version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    public abstract RecipeDao recipeDao();

}
