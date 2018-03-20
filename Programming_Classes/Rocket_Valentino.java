public class Rocket_Valentino {

	public static void main(String[] args) {
        // Initialize variables
		// ADD CODE HERE

        double time = 0.0;
        double y = 0.0;
        double v = 0.0;
        double a = 0.0;
        double deltaTime = 1.0e-3;
	double dragCoefficient = 0.5;
	double emptyMass = 10000.0;
	double fuelMass = 10000.0;
	double payLoadMass = 4255;
	double exhaustVelocity = 7000.0;
	double crossSection = 10.0;
	double rhoNaught = 1.225;
	double atmosphericMolarMass = 0.0289644;
	double idealGasConstant = 8.314;
	double radiusEarth = 6.371e6;
	double burnRate = 100.0;
	double massEarth = 5.972e24;
        double temperatureAtoms = 255.0;
	double g = 9.807;
	double G = 6.673e-11;
        // For Each Timestep
        while(v>=0.0) {
		
		double totalMass = emptyMass + fuelMass + payLoadMass;
           	 // Calculate acceleration
                //Fthrust = (exhaustVelocity/totalMass)*burnRate;	
                // Calculate air density
                double airDensity = rhoNaught*Math.exp(-((g*atmosphericMolarMass*y)/(idealGasConstant*temperatureAtoms)));

        	    // Calculate drag
                double Fdrag = 0.5*airDensity*v*v*dragCoefficient * crossSection/totalMass;	

                // Calculate gravity
                double gravity = G*((massEarth)/(Math.pow(radiusEarth,2)));

                // If fuel remains, calculate thrust
		double Fthrust = 0.0;
                if(fuelMass > 0.0) Fthrust = exhaustVelocity/totalMass*burnRate;

                // Acceleration = (thrust – gravity – drag)
                a = Fthrust - gravity - Fdrag;
            // Calculate new velocity (v = v + a * dt)
            v += a*deltaTime;

            // Calculate new position (y = y + v * dt)
            y += v*deltaTime;

            // Calculate new fuel mass
            if(Fthrust>0.0)  fuelMass -= burnRate*deltaTime;
	    

            // Calculate escape velocity
            double escapeVelocity = Math.sqrt(2*G*massEarth/(radiusEarth+y)); 
            // Output values at each timestep
            System.out.printf("%9.3f %9.3f %9.3f %9.3f %9.3f\n",time,y,
                            v,escapeVelocity,fuelMass);
            // update time
            time += deltaTime;
	    /*if(fuelMass  <= 0)
	    {
		    System.out.println(escapeVelocity);
		    v = -1;
	    }*/
		
	}
    }
}
