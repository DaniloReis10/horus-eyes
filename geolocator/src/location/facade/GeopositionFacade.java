package location.facade;

import java.util.List;

import location.geoengine.DevicePath;


public class GeopositionFacade implements IGeoPositionControl{

	
	public GeopositionFacade(){
		super();
	}

	@Override
	public List<IMobileDevice> getMobileDevicesInArea(IMobileDevice device,
			double width, double height) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IMobileDevice> getMobileDevicesAround(IMobileDevice device,
			double ratio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMobileDevice getClosestMobileDevice(IMobileDevice device) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean addMobileDeviceToTrack(IMobileDevice device) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public DevicePath getMobileDeviceTrack(IMobileDevice device) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getMobileDeviceMap(IMobileDevice device) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateMobileDevicePosition(IMobileDevice device,
			IGeoPosition position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IMobileDevice findMobileDeviceById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IMobileDevice> getMobileDevicesByGroup(int groupId) {
		// TODO Auto-generated method stub
		return null;
	}



}
