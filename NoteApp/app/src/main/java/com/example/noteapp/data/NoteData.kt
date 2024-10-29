package com.example.noteapp.data

import com.example.noteapp.model.Note

class NoteDataSource {
    fun loadNotes(): List<Note> {
        return listOf(
            Note(title = "A good day", description = "We went on a vacation"),
            Note(
                title = "Sunset Bliss",
                description = "Watched the sun go down over the hills"
            ),
            Note(
                title = "Mountain Adventure",
                description = "Hiked up the rocky trails with amazing views"
            ),
            Note(
                title = "Seaside Stroll",
                description = "Walked along the beach, waves crashing nearby"
            ),
            Note(
                title = "City Lights",
                description = "Explored the bustling streets and neon signs"
            ),
            Note(
                title = "Forest Escape",
                description = "Relaxed among the tall trees and fresh air"
            ),
            Note(title = "Campfire Night", description = "Shared stories and laughs by the fire"),
            Note(title = "Road Trip", description = "Drove through scenic routes and small towns"),
            Note(title = "Lakeside Picnic", description = "Enjoyed a quiet meal by the lake"),
            Note(title = "Snowy Adventure", description = "Built snowmen and had a snowball fight"),
            Note(title = "Desert Safari", description = "Took a ride through the sandy dunes")

        )
    }
}