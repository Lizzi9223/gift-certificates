'use strict';

/**
 * You must return a date that comes in a predetermined number of seconds after 01.06.2020 00:00:00.2020
 * @param {number} seconds
 * @returns {Date}
 *
 * @example
 *      31536000 -> 01.06.2021
 *      0 -> 01.06.2020
 *      86400 -> 02.06.2020
 */
function secondsToDate(seconds) {
    let startDate = Date.parse('2020-06-01T00:00:00.2020');
    let resultDate = new Date(startDate + seconds * 1000);
    const yyyy = resultDate.getFullYear();
    let mm = resultDate.getMonth() + 1; // Months start at 0!
    let dd = resultDate.getDate();    
    if (dd < 10) dd = '0' + dd;
    if (mm < 10) mm = '0' + mm;    
    const formattedToday = dd + '/' + mm + '/' + yyyy;
    console.log(seconds + " -> " + formattedToday);
    return formattedToday;
}

/**
 * You must create a function that returns a base 2 (binary) representation of a base 10 (decimal) string number
 * ! Numbers will always be below 1024 (not including 1024)
 * ! You are not able to use parseInt
 * @param {number} decimal
 * @return {string}
 *
 * @example
 *      5 -> "101"
 *      10 -> "1010"
 */
function toBase2Converter(decimal) {
    let number = decimal;
    let binary = (number % 2).toString();
    while(number > 1){
        number = parseInt(number / 2);
        binary = (number % 2) + (binary);
    }
    console.log(decimal + " -> " + binary);
}

/**
 * You must create a function that takes two strings as arguments and returns the number of times the first string
 * is found in the text.
 * @param {string} substring
 * @param {string} text
 * @return {number}
 *
 * @example
 *      'a', 'test it' -> 0
 *      't', 'test it' -> 2
 *      'T', 'test it' -> 2
 */
function substringOccurrencesCounter(substring, text) {
    const regex = new RegExp(substring.toLowerCase(), 'g');
    var count = (text.toLowerCase().match(regex) || []).length;
    console.log("'" + substring + "', " + "'" + text + "' -> " + count);
}

/**
 * You must create a function that takes a string and returns a string in which each character is repeated once.
 *
 * @param {string} string
 * @return {string}
 *
 * @example
 *      "Hello" -> "HHeelloo"
 *      "Hello world" -> "HHeello  wworrldd" // o, l is repeated more then once. Space was also repeated
 */
function repeatingLitters(string) {
    let letters = new Set();
    let result = "";
    for (let i = 0; i < string.length; i++) {
        if (letters.has(string[i])) {            
            result += string[i];
        } else {
            result += string[i].repeat(2);
            letters.add(string[i], 1);
        }
        if(i<string.length-1 && string[i+1]===string[i])
            i++;
    }
    console.log("\"" + string + "\" -> \"" + result + "\"");
}

/**
 * You must write a function redundant that takes in a string str and returns a function that returns str.
 * ! Your function should return a function, not a string.
 *
 * @param {string} str
 * @return {function}
 *
 * @example
 *      const f1 = redundant("apple")
 *      f1() ➞ "apple"
 *
 *      const f2 = redundant("pear")
 *      f2() ➞ "pear"
 *
 *      const f3 = redundant("")
 *      f3() ➞ ""
 */
function redundant(str) {
    return function (){
        return str;
    }
}

/**
 * https://en.wikipedia.org/wiki/Tower_of_Hanoi
 *
 * @param {number} disks
 * @return {number}
 */
function towerHanoi(disks) {

}

/**
 * You must create a function that multiplies two matricies (n x n each).
 *
 * @param {array} matrix1
 * @param {array} matrix2
 * @return {array}
 *
 */
function matrixMultiplication(matrix1, matrix2) {

}

/**
 * Create a gather function that accepts a string argument and returns another function.
 * The function calls should support continued chaining until order is called.
 * order should accept a number as an argument and return another function.
 * The function calls should support continued chaining until get is called.
 * get should return all of the arguments provided to the gather functions as a string in the order specified in the order functions.
 *
 * @param {string} str
 * @return {string}
 *
 * @example
 *      gather("a")("b")("c").order(0)(1)(2).get() ➞ "abc"
 *      gather("a")("b")("c").order(2)(1)(0).get() ➞ "cba"
 *      gather("e")("l")("o")("l")("!")("h").order(5)(0)(1)(3)(2)(4).get()  ➞ "hello"
 */
function gather(str) {

}

console.log("\n--- Task 1. secondsToDate ---");
secondsToDate(31536000);
secondsToDate(0);
secondsToDate(86400);

console.log("\n--- Task 2. toBase2Converter ---");
toBase2Converter(5);
toBase2Converter(10);

console.log("\n--- Task 3. substringOccurrencesCounter ---");
substringOccurrencesCounter('a', 'test it');
substringOccurrencesCounter('t', 'test it');
substringOccurrencesCounter('T', 'test it');

console.log("\n--- Task 4. repeatingLitters ---");
repeatingLitters('Hello');
repeatingLitters('Hello world');

console.log("\n--- Task 5. redundant ---");
const f1 = redundant("apple");
console.log("f1() -> " + f1());
const f2 = redundant("pear");
console.log("f2() -> " + f2());
const f3 = redundant("");
console.log("f3() -> " + f3());