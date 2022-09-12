
var certificates = [
    {
        "id": 1,
        "name":"Helicopter ride",
        "description":"Description description description description description description description description description",
        "price":"10$",
        "duration":30,
        "create_date":"2016-04-26T18:09:16Z",
        "last_update_date":"2016-04-26T18:09:16Z",
        "image_src":"certificates_images/helicopter.jpg",
        "tags":[
            "Vehicles", "Extreme"
        ]
    },
    {
        "id": 2,
        "name":"Make up",
        "description":"Description description description description description description description description description",
        "price":"10$",
        "duration":30,
        "create_date":"2022-04-26T18:09:16Z",
        "last_update_date":"2022-04-26T18:09:16Z",
        "image_src":"certificates_images/makeup.jpg",
        "tags":[
            "Makeup", "Selfcare"
        ]
    },
    {
        "id": 3,
        "name":"Motorbike ride",
        "description":"Description description description description description description description description description",
        "price":"10$",
        "duration":30,
        "create_date":"2022-08-23T18:09:16Z",
        "last_update_date":"2022-08-23T18:09:16Z",
        "image_src":"certificates_images/motorbike.jpg",
        "tags":[
            "Vehicles", "Extreme"
        ]
    },
    {
        "id": 4,
        "name":"Air balloon ride",
        "description":"Description description description description description description description description description",
        "price":"150$",
        "duration":30,
        "create_date":"2022-07-23T18:09:16Z",
        "last_update_date":"2022-07-23T18:09:16Z",
        "image_src":"certificates_images/air_balloon.jpg",
        "tags":[
            "Vehicles", "Extreme"
        ]
    },
    {
        "id": 5,
        "name":"Yoga sessions in morning",
        "description":"Description description description description description description description description description",
        "price":"10$",
        "duration":30,
        "create_date":"2022-08-24T18:09:16Z",
        "last_update_date":"2022-08-24T18:09:16Z",
        "image_src":"certificates_images/yoga.jpg",
        "tags":[
            "Selfcare"
        ]
    },
    {
        "id": 6,
        "name":"Zoo session",
        "description":"Description description description description description description description description description",
        "price":"17$",
        "duration":30,
        "create_date":"2021-08-23T18:09:16Z",
        "last_update_date":"2021-08-23T18:09:16Z",
        "image_src":"certificates_images/zoo.jpg",
        "tags":[
            "Animals", "Kids"
        ]
    },
    {
        "id": 7,
        "name":"Photoshooting 2hr",
        "description":"Description description description description description description description description description",
        "price":"10$",
        "duration":30,
        "create_date":"2020-08-23T18:09:16Z",
        "last_update_date":"2020-08-23T18:09:16Z",
        "image_src":"certificates_images/photoshooting.jpg",
        "tags":[
            "Photoshoot"
        ]
    },
    {
        "id": 8,
        "name":"Massage",
        "description":"Description description description description description description description description description",
        "price":"25$",
        "duration":30,
        "create_date":"2021-02-01T18:09:16Z",
        "last_update_date":"2021-02-01T18:09:16Z",
        "image_src":"certificates_images/massage.jpg",
        "tags":[
            "Selfcare"
        ]
    },
    {
        "id": 9,
        "name":"Cooking with chef",
        "description":"Description description description description description description description description description",
        "price":"10$",
        "duration":30,
        "create_date":"2022-08-23T18:09:16Z",
        "last_update_date":"2022-08-23T18:09:16Z",
        "image_src":"certificates_images/cooking.jpg",
        "tags":[
            "Food"
        ]
    },
    {
        "id": 10,
        "name":"Hair & MakeUp",
        "description":"Description description description description description description description description description",
        "price":"10$",
        "duration":30,
        "create_date":"2022-01-07T18:09:16Z",
        "last_update_date":"2022-01-07T18:09:16Z",
        "image_src":"certificates_images/hair.jpg",
        "tags":[
            "Selfcare", "Makeup"
        ]
    },
    
    
    
];


var tags = [
    {
        "id": 1,
        "name": "Vehicles",
        "image_src":"tags_images/vehicle.jpg"
    },
    {
        "id": 2,
        "name": "Selfcare",
        "image_src":"tags_images/selfcare.jpg"
    },
    {
        "id": 3,
        "name": "Animals",
        "image_src":"tags_images/Animals.jpg"
    },
    {
        "id": 4,
        "name": "Food",
        "image_src":"tags_images/Food.jpg"
    },
    {
        "id": 5,
        "name": "Extreme",
        "image_src":"tags_images/Extreme.jpg"
    },
    {
        "id": 6,
        "name": "Photoshoot",
        "image_src":"tags_images/photoshoot.jpg"
    },
    {
        "id": 7,
        "name": "Makeup",
        "image_src":"tags_images/Makeup.jpg"
    },
    {
        "id": 8,
        "name": "Kids",
        "image_src":"tags_images/Kids.jpg"
    },
];



window.localStorage.setItem('certificates', JSON.stringify(certificates));
window.localStorage.setItem('tags', JSON.stringify(tags));


 
 