package vehicles;

public enum VehicleType {
	CAR
	{		
		public Vehicle newInstance(String[] args)
		{
			return new Car(args);
		}
	},
	BOAT
	{		
		public Vehicle newInstance(String[] args)
		{
			return new Boat(args);
		}
	},
	PLANE
	{		
		public Vehicle newInstance(String[] args)
		{
			return new Plane(args);
		}
	};
	
	public abstract Vehicle newInstance(String[] args);
}
