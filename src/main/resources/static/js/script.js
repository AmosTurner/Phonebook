// function getContact(id) {
//     if (document.getElementById("contact" + id).innerHTML == "") {
//         document.getElementById("contact" + id).innerHTML = "hello!";
//         fetch('http://localhost:8080/getContact/' + id)  // fetch data from our service
//             .then(data => data.json()) // JSONify the data returned
//             .then(function (data) { // with the JSON data
// // modify textToDisplay below here!
//                 var textToDisplay=""; // create and append to a blank var
//                 textToDisplay+= "ID: " + data.contact.id + "<br>";
//                 textToDisplay+= "Name: " + data.contact.name + "<br>";
//                 // textToDisplay+= "Grade: " + data.contact.phoneNumber+ "<br>";
//                 // textToDisplay+= "Letter Grade: " + data.contact.email+ "<br>";
//                 // textToDisplay+= "Attended: " + data.student.address+ "<br>";
// // finally, change our relevant div to display the var
//                 document.getElementById("contact" + id).innerHTML = textToDisplay;
//             });
//
//     } else {
//         document.getElementById("contact" + id).innerHTML = "";
//     }
// }
