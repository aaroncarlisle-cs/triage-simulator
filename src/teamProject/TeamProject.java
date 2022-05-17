package teamProject;

import java.awt.EventQueue;

//Test client
public class TeamProject {
	public static void main(String[] args) {
		
		//Initialize our clinic

		
		//Launch GUI
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppWindow window = Clinic.initialize();;
					window.initialize();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}
}
