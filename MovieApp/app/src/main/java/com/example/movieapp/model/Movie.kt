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
                "https://lumiere-a.akamaihd.net/v1/images/cg_avatar_expansion_dlr_a58f2121.jpeg?region=0,0,1920,1080&width=600",
                "https://lumiere-a.akamaihd.net/v1/images/cg_frontiers_of_pandora_dlc_570b29f5.jpeg?region=0,0,1920,1080&width=600",
                "https://lumiere-a.akamaihd.net/v1/images/cg_avatar_pandorapedia_extra_fc229ce4.jpeg?region=0,0,1536,864&width=600"
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
                "https://ew.com/thmb/WJJxIbUia1LztJxKZHAOtXNOjcQ=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/08-fifty-shades-of-grey_0_0-453ec307a4a148f09de83e11ae40ccf3.jpg",
                "https://media.newyorker.com/photos/5a7dd2e752e589162b84ff3c/master/w_2560%2Cc_limit/Brody-Fifty-Shades-Freed.jpg",
                "https://statcdn.fandango.com/MPX/image/NBCU_Fandango/210/743/FiftyShadesofGrey_Trailer2.jpg"
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
                "https://img.etimg.com/thumb/width-1200,height-900,imgsize-939867,resizemode-75,msid-71067645/magazines/panache/chhichhore-wins-hearts-all-set-to-make-its-entry-into-rs-50-cr-club.jpg",
                "https://img.etimg.com/thumb/width-1200,height-900,imgsize-222111,resizemode-75,msid-71045547/magazines/panache/chhichhore-review-likeable-film-with-relevant-social-message.jpg",
                "https://sc0.blr1.cdn.digitaloceanspaces.com/article/126826-zbtzbalmdm-1567663037.jpeg"
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
                "https://i.ytimg.com/vi/u9JWSqLXyY0/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLAB3BYSzkJPJuTfehUbTWvNptO9EA",
                "https://en.vogue.me/wp-content/uploads/2024/06/promo-harry-potter.jpg",
                "https://platform.vox.com/wp-content/uploads/sites/2/chorus/uploads/chorus_asset/file/14770893/3176173-1748009911-hp.jp_.0.1547203154.jpg?quality=90&strip=all&crop=7.8125,0,84.375,100"
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
                "https://resizing.flixster.com/W15dNIYCMxFtwADEUFrQDUj0Q2g=/fit-in/705x460/v2/https://resizing.flixster.com/-XZAfHZM39UwaGJIFWKAE8fS0ak=/v3/t/assets/p7951929_v_v10_af.jpg",
                "https://cdn.britannica.com/33/199933-050-469591B9/Aamir-Khan-Madhavan-Sharman-Joshi-3-Idiots.jpg",
                "https://images.indianexpress.com/2019/10/3-idiots-759.jpg"
            ),
            rating = "8.4"
        ),
        Movie(
            id = "6",
            title = "আবার প্রলয়",
            year = "2013",
            genre = "Action, Thriller",
            director = "Arindam Sil",
            actors = "Saswata Chatterjee, Paran Bandopadhyay, Koushani Mukherjee",
            plot = "A thrilling tale of cops trying to crack down on a human trafficking racket in the Sunderbans.",
            poster = "https://m.media-amazon.com/images/M/MV5BYjExMmU1ODUtYzQ4YS00MWIyLWI4ZDUtMTU2MmVjZGU2YjI3XkEyXkFqcGc@._V1_.jpg",
            images = listOf(
                "https://akamaividz2.zee5.com/image/upload/w_1101,h_620,c_scale,f_webp,q_auto:eco/resources/0-1-6z5414957/list/abarproloyraile11170x658080820237920a549abe7464fbfa397cf8ddc17c3.jpg",
                "https://a10.gaanacdn.com/gn_img/albums/MmqK5pEbwR/qK5ZVq7X3w/size_m.jpg",
                "https://images.ottplay.com/images/gaurav-and-nusraat-faria-428.jpg?impolicy=ottplay-20210210&width=1200&height=675",
                "https://i.gadgets360cdn.com/products/large/Abar-Proloy-3-1198x800-1719195220350.jpg?downsize=680:*",
                "https://a10.gaanacdn.com/gn_img/albums/YoEWlwa3zX/EWloA8pz3z/size_m.jpg"
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
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcToD0YoaOphdG6sqRwBu05wgzJl38fwUwhISA&s",
                "https://st1.bollywoodlife.com/wp-content/uploads/2019/10/Housefull-4-8.jpg?impolicy=Medium_Widthonly&w=412&h=290",
                "https://www.bollywoodhungama.com/wp-content/uploads/2019/11/Box-Office-Housefull-4-Day-18-in-overseas.jpg"
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
                "https://m.media-amazon.com/images/M/MV5BMzczNDgyZjMtMjM1Ni00NDAyLTk3MmMtYmNlYmYxZWMzYzg1XkEyXkFqcGc@._V1_.jpg",
                "https://assets.timelinedaily.com/w/1203x902/2024/07/snapinsta-app_448546227_1714974559035723_2910903958553085327_n_1080.jpg.webp",
                "https://images.thedirect.com/media/article_full/IMG_4165.jpg"
            ),
            rating = "8.5"
        )
    )
}
