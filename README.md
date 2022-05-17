# triage-simulator
Team Project for CSIS 2420
By Aaron Carlisle and Hayden Pack
April 2022

Driver class: TeamProject.java

Description:
This program is a proof of concept and would be used by a fictitious hospital emergency room to triage patients. The patients are placed in a priority queue system based on the severity of their injuries. Patients may present with one of five injury types, and one of three insurance types. There are three doctors. Each doctor treats some combination of one or more injury types. A patient cannot be treated by a provider who does not treat their injury type. Each doctor also accepts some combination of one or more insurance types. They will treat patients with insurances they do not accept, but this will incur a higher cost.

Each provider object has an associated max priority queue using a binary heap data structure. Insertion and deletion operations take log(n) time, where n is the number of elements in the priority queue. The connections between providers, injuries, and insurance types are represented as a single directed graph. A breadth first search algorithm is used to determine whether any two elements are connected, and for particular cases, the number of nodes between connections are used to help determine the patient’s bill. The breadth first search runs in O(E + V) time where E is the number of edges and V is the number of vertices for the directed graph. Instance methods to determine if two nodes are connected and how many nodes there are between connections run in constant O(1) time.

The GUI is constructed using Java Swing and custom renderers are used to override the toString methods when displaying providers, injuries, and insurances for JComboBoxes. The CardLayout API is used to display the different panels of the GUI and triggered by event listeners for the menu panel.
 

