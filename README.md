This mobile is designed to help users better control their inventory for merchendise.
It has a log in screen where users can keep the information they store on the app secure.
The main page has information about each item stored.
Clicking on the items name will take the user to an edit screen where the user can change the count if the item.
There is also an option to delete the item from the edit screen.
The home screen has an add item button. This takes the user to another screen.
The user can enter the item name, quantity, and a description of the item.
The last screen is for registering a new user and can be navigated to by pressing a button from the log in screen.

There is an SQLite storage file for both the inventory system and the users.
The app is set up to notify the user when inventory reaches zero. 
This is so they can decide whether to get more quantity or delete the item from their inventory.
This means that the app needs to request permission for SMS notifications.
A button in the add item screen allows the user to request this.

The UI was set up with simplicity in mind. It only a few colors for each screen.
The colors are also consistent across the entire app.
Text boxes are used for each text entry item. A descripter word is visable in each feild to help the user know how to navigate the app.

While designing this app, I focused on designing the pages for each screen in the UI. 
Focusing in this without putting the same planning into designing how the app would function made development more difficult.
There was basically three things to design: the xml code for each page, java code for page navigation, and java code for the functionality of the app.
Since most of my focus was on UI amd the xml code, testing was done by running the app to see if it worked how I intended.
If i would have put equal planning into designing the functionality of the items in the inventory objects I would have made JUnit tests to make sure this worked as well.
In the future I would design an apps funtionality first and then design the UI components to help a user navigate this functionality.
