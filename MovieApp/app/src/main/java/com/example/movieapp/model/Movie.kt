package com.example.movieapp.model

data class Movie(
    val id: String,
    val title: String,
    val year: String,
    val genre: String,
    val director: String,
    val actors: String,
    val plot: String,
    val poster: String,
    val images: List<String>,
    val rating: String
)

fun getMovies(): List<Movie> {
    return listOf(

        Movie(
            id = "1",
            title = "Avatar",
            year = "2009",
            genre = "Action, Adventure, Fantasy",
            director = "James Cameron",
            actors = "Sam Worthington, Zoe Saldana, Sigourney Weaver",
            plot = "A paraplegic Marine is dispatched to the moon Pandora on a unique mission, but becomes torn between following orders and protecting an alien civilization.",
            poster = "https://m.media-amazon.com/images/I/61l4F+4vu8L._AC_.jpg",
            images = listOf(
                "https://example.com/avatar_image1.jpg",
                "https://example.com/avatar_image2.jpg",
                "https://example.com/avatar_image3.jpg"
            ),
            rating = "7.8"
        ),
        Movie(
            id = "2",
            title = "Fifty Shades Of Grey",
            year = "2015",
            genre = "Drama, Romance",
            director = "Sam Taylor-Johnson",
            actors = "Dakota Johnson, Jamie Dornan, Jennifer Ehle",
            plot = "Literature student Anastasia Steele's life changes forever when she meets handsome, yet tormented, billionaire Christian Grey.",
            poster = "https://m.media-amazon.com/images/I/714dsIq4FXL._AC_SY879_.jpg",
            images = listOf(
                "https://example.com/fifty_shades_image1.jpg",
                "https://example.com/fifty_shades_image2.jpg",
                "https://example.com/fifty_shades_image3.jpg"
            ),
            rating = "4.1"
        ),
        Movie(
            id = "3",
            title = "Chichore",
            year = "2019",
            genre = "Comedy, Drama",
            director = "Nitesh Tiwari",
            actors = "Sushant Singh Rajput, Shraddha Kapoor, Varun Sharma",
            plot = "A tragic incident forces Anirudh, a middle-aged man, to take a trip down memory lane and reminisce his college days with his friends, who were labeled as losers.",
            poster = "https://i.pinimg.com/474x/71/6f/26/716f26707b872e00232f86084db053bd.jpg",
            images = listOf(
                "https://example.com/chichore_image1.jpg",
                "https://example.com/chichore_image2.jpg",
                "https://example.com/chichore_image3.jpg"
            ),
            rating = "8.3"
        ),
        Movie(
            id = "4",
            title = "Harry Potter",
            year = "2001",
            genre = "Adventure, Fantasy",
            director = "Chris Columbus",
            actors = "Daniel Radcliffe, Emma Watson, Rupert Grint",
            plot = "An eleven-year-old boy discovers that he is the orphaned son of two powerful wizards and possesses unique magical powers.",
            poster = "https://m.media-amazon.com/images/I/81Sx0PJXN1L._AC_UF894,1000_QL80_.jpg",
            images = listOf(
                "https://example.com/harry_potter_image1.jpg",
                "https://example.com/harry_potter_image2.jpg",
                "https://example.com/harry_potter_image3.jpg"
            ),
            rating = "7.6"
        ),
        Movie(
            id = "5",
            title = "3 Idiots",
            year = "2009",
            genre = "Comedy, Drama",
            director = "Rajkumar Hirani",
            actors = "Aamir Khan, R. Madhavan, Sharman Joshi",
            plot = "Two friends are searching for their long lost companion. They revisit their college days and recall the memories of their friend who inspired them to think differently.",
            poster = "https://m.media-amazon.com/images/I/61sZGNr75iL._AC_UF894,1000_QL80_.jpg",
            images = listOf(
                "https://example.com/3_idiots_image1.jpg",
                "https://example.com/3_idiots_image2.jpg",
                "https://example.com/3_idiots_image3.jpg"
            ),
            rating = "8.4"
        ),
        Movie(
            id = "6",
            title = "Abar Proloy",
            year = "2013",
            genre = "Action, Thriller",
            director = "Arindam Sil",
            actors = "Saswata Chatterjee, Paran Bandopadhyay, Koushani Mukherjee",
            plot = "A thrilling tale of cops trying to crack down on a human trafficking racket in the Sunderbans.",
            poster = "https://m.media-amazon.com/images/M/MV5BYjExMmU1ODUtYzQ4YS00MWIyLWI4ZDUtMTU2MmVjZGU2YjI3XkEyXkFqcGc@._V1_.jpg",
            images = listOf(
                "https://example.com/abar_proloy_image1.jpg",
                "https://example.com/abar_proloy_image2.jpg",
                "https://example.com/abar_proloy_image3.jpg"
            ),
            rating = "7.2"
        ),
        Movie(
            id = "7",
            title = "HouseFull",
            year = "2010",
            genre = "Comedy, Drama",
            director = "Sajid Khan",
            actors = "Akshay Kumar, Deepika Padukone, Riteish Deshmukh",
            plot = "A man, suffering from bad luck, gets involved in a series of comedic events when he tries to marry into a wealthy family.",
            poster = "https://upload.wikimedia.org/wikipedia/en/3/3c/Housefull_4_poster.jpg",
            images = listOf(
                "https://example.com/housefull_image1.jpg",
                "https://example.com/housefull_image2.jpg",
                "https://example.com/housefull_image3.jpg"
            ),
            rating = "5.4"
        ),
        Movie(
            id = "8",
            title = "Mirzapur",
            year = "2018",
            genre = "Action, Crime, Drama",
            director = "Karan Anshuman",
            actors = "Pankaj Tripathi, Ali Fazal, Divyendu Sharma",
            plot = "A shocking incident at a wedding changes the lives of two families and sets the stage for an action-packed journey of revenge, betrayal, and a fight for power.",
            poster = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJYlKNoaZISzWeTbuOGwLPnWuM4R6i8_K1WCYB6YM6JH8qcnHSxIPaf1FpgQYV7kl-F_UP1NYymVflYMJ_2yZ131yDw-2fwQmK-VFfXQ",
            images = listOf(
                "https://example.com/mirzapur_image1.jpg",
                "https://example.com/mirzapur_image2.jpg",
                "https://example.com/mirzapur_image3.jpg"
            ),
            rating = "8.5"
        )
    )
}
