package com.ms.common;

import org.locationtech.proj4j.BasicCoordinateTransform;
import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.CoordinateTransform;
import org.locationtech.proj4j.CoordinateTransformFactory;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
public class TransCoord {
	public ProjCoordinate transform(String strLon, String strLat) {

		// parse to Double
		Double dblLon = Double.parseDouble(strLon);
		Double dblLat = Double.parseDouble(strLat);

		CRSFactory factory = new CRSFactory();
		CoordinateReferenceSystem epsg = factory.createFromParameters("EPSG:2097", "+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs");
		CoordinateReferenceSystem wgs84 = factory.createFromParameters("WGS84", "+proj=longlat +datum=WGS84 +no_defs");
		BasicCoordinateTransform transformer = new BasicCoordinateTransform(epsg, wgs84);

		ProjCoordinate beforeCoord = new ProjCoordinate(dblLon, dblLat);
		ProjCoordinate afterCoord = new ProjCoordinate();
		
		afterCoord = transformer.transform(beforeCoord, afterCoord);

		return afterCoord;
	}
	
}
