package com.example.mycalculator.Data.HomeScreenData

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.mycalculator.Data.GroupScreenData.ContactGroup


@Entity(
    tableName = "DummyContacts"
)
data class DummyContacts(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var groupId: Int,
    var name: String,
    var phoneNumber: String,
    var email: String,
    var dob: String,
)


//fun loadDummyContacts(): List<DummyContacts> {
//    return listOf(
//        DummyContacts(
//            id = 1,
//            name = "Atrajit",
//            phoneNumber = "+918101858360",
//            email = "atrajit.sarkar@gmail.com",
//            dob = "13/09/2000"
//        ),
//        DummyContacts(
//            id = 2,
//            name = "Neeshu",
//            phoneNumber = "+919876543210",
//            email = "neeshu.123@gmail.com",
//            dob = "25/12/1998"
//        ),
//        DummyContacts(
//            id = 3,
//            name = "Samaresh",
//            phoneNumber = "+918765432109",
//            email = "samaresh.das@example.com",
//            dob = "15/08/1995"
//        ),
//        DummyContacts(
//            id = 4,
//            name = "Rohit",
//            phoneNumber = "+917654321098",
//            email = "rohit.kumar@example.com",
//            dob = "01/01/2002"
//        ),
//        DummyContacts(
//            id = 5,
//            name = "Priya",
//            phoneNumber = "+916543210987",
//            email = "priya.sharma@example.com",
//            dob = "10/05/1997"
//        ),
//        DummyContacts(
//            id = 6,
//            name = "Aarav",
//            phoneNumber = "+915432109876",
//            email = "aarav.verma@example.com",
//            dob = "18/03/1999"
//        ),
//        DummyContacts(
//            id = 7,
//            name = "Riya",
//            phoneNumber = "+914321098765",
//            email = "riya.agarwal@example.com",
//            dob = "22/07/2001"
//        ),
//        DummyContacts(
//            id = 8,
//            name = "Kabir",
//            phoneNumber = "+913210987654",
//            email = "kabir.jain@example.com",
//            dob = "30/11/1990"
//        ),
//        DummyContacts(
//            id = 9,
//            name = "Sneha",
//            phoneNumber = "+912109876543",
//            email = "sneha.patel@example.com",
//            dob = "14/02/1996"
//        ),
//        DummyContacts(
//            id = 10,
//            name = "Aryan",
//            phoneNumber = "+911098765432",
//            email = "aryan.gupta@example.com",
//            dob = "05/06/2003"
//        ),
//        DummyContacts(
//            id = 11,
//            name = "Maya",
//            phoneNumber = "+919876543321",
//            email = "maya.sen@example.com",
//            dob = "27/10/1989"
//        ),
//        DummyContacts(
//            id = 12,
//            name = "Vikram",
//            phoneNumber = "+918765432221",
//            email = "vikram.roy@example.com",
//            dob = "02/04/1992"
//        ),
//        DummyContacts(
//            id = 13,
//            name = "Ananya",
//            phoneNumber = "+917654321120",
//            email = "ananya.nair@example.com",
//            dob = "19/09/1994"
//        ),
//        DummyContacts(
//            id = 14,
//            name = "Krishna",
//            phoneNumber = "+916543211110",
//            email = "krishna.mehta@example.com",
//            dob = "11/12/1988"
//        ),
//        DummyContacts(
//            id = 15,
//            name = "Ishita",
//            phoneNumber = "+915432100009",
//            email = "ishita.khan@example.com",
//            dob = "20/01/1993"
//        ),
//        DummyContacts(
//            id = 16,
//            name = "Rahul",
//            phoneNumber = "+914321098800",
//            email = "rahul.bose@example.com",
//            dob = "03/03/2001"
//        ),
//        DummyContacts(
//            id = 17,
//            name = "Tanya",
//            phoneNumber = "+913210987700",
//            email = "tanya.kaur@example.com",
//            dob = "12/07/1998"
//        ),
//        DummyContacts(
//            id = 18,
//            name = "Aditya",
//            phoneNumber = "+912109876600",
//            email = "aditya.singh@example.com",
//            dob = "28/09/1995"
//        ),
//        DummyContacts(
//            id = 19,
//            name = "Pooja",
//            phoneNumber = "+911098765500",
//            email = "pooja.das@example.com",
//            dob = "06/05/1991"
//        ),
//        DummyContacts(
//            id = 20,
//            name = "Kunal",
//            phoneNumber = "+919876544444",
//            email = "kunal.jain@example.com",
//            dob = "09/11/1990"
//        )
//    )
//}

