# mobile-treasure-hunt
An assignment for my Mobile App Development class at Oregon State Univeristy.

![image](https://github.com/wleejess/mobile-treasure-hunt/assets/29618012/db99bb20-d499-40c3-808a-877d60206118)


The app must meet the following requirements:

    ALL information for the treasure hunt should be loaded from a resource file.
    The app should present a permissions page first, for the user to approve the various permissions required to run the game.

The app will present a Start page that contains the title, rules for the game, and a start button. The rules should be scrollable to present all information.
The Start Button takes you to the "Clue" page. There are (at least) two clues to solve in the app. The Clue Page contains:

    A textual clue to a real-world location.
        Example Clue: "Grab some things for dinner but keep an eye on the time" - Real-world Location:  An open-air Market with a large clock.
    A hint button
        Gives further helpful info. Example Hint: "Fish sounds good for dinner" (perhaps the Market is known for several fish-sellers).
    An animated count-up timer. Shows the time elapsed since the Start Button was pressed.
    A "Found It!" button. User presses this when they think they are at the location referenced by the clue.
    A quit button that exits the treasure hunt and takes the user to the Start Page.

The "Found It!" button will cause the app to check if the user has indeed found the correct location:

    If not the correct location, a popup informs the user. The timer continues to run.
    If the user IS at the correct location:
        If this IS NOT the final Clue (there are more clues to solve), then the "Clue Solved" page is presented. The page will have the following:
            The count-up timer will be PAUSED while on the Clue Solved page, but the elapsed time since Start was pressed will still be shown. This allows the user to take time to read the info about the location.
            More info about the location. Example: "The Pike Place Market was founded in 1907..."
            A Continue button which will take the user back to the Clue page and present the next clue.
        If this IS the final Clue (no more clues to solve), then the "Treasure Hunt Completed" page is presented.

The "Treasure Hunt Completed" page presents:

    A congratulatory message
    The total elapsed time since the Start button was pressed (and of course the timer is stopped).
    Info about the final Clue location.
    A home button that takes the user to the Start Page.

<img width="200" alt="image" src="https://github.com/wleejess/mobile-treasure-hunt/assets/29618012/3a040050-3349-4cac-8ead-9348c42f43b0">
<img width="200" alt="image" src="https://github.com/wleejess/mobile-treasure-hunt/assets/29618012/ec9de151-8367-426b-a526-2f01afa22cdd">
<img width="200" alt="image" src="https://github.com/wleejess/mobile-treasure-hunt/assets/29618012/32be897d-2c7f-4ba7-b45d-44bd12c70e40">
</br>
<img width="200" alt="image" src="https://github.com/wleejess/mobile-treasure-hunt/assets/29618012/918596cb-5f37-4ebe-b034-7fd950622bb7">
<img width="200" alt="image" src="https://github.com/wleejess/mobile-treasure-hunt/assets/29618012/828e1bbb-cef6-4e46-99c6-9fd96de5092c">
<img width="200" alt="image" src="https://github.com/wleejess/mobile-treasure-hunt/assets/29618012/a2a7bdba-3eb4-41e9-862b-460cfc997f94">

