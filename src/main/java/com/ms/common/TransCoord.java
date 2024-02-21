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
@Slf4j
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

		log.info("$$$$$$$$$$$$$$"+ afterCoord);
		return afterCoord;
	}
	
	public ProjCoordinate transformtwo(String strLon, String strLat) {

		// CRS 객체 생성
		CRSFactory crsFactory = new CRSFactory();

		// WGS84 system 정의
		String wgs84Name = "WGS84";
		String wgs84Proj = "+proj=longlat +datum=WGS84 +no_defs";
		CoordinateReferenceSystem wgs84System = crsFactory.createFromParameters(wgs84Name, wgs84Proj);

		// UTMK system 정의
		String utmkName = "UTMK";
		String utmkProj = "+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=GRS80 +units=m +no_defs";
		CoordinateReferenceSystem utmkSystem = crsFactory.createFromParameters(utmkName, utmkProj);

		// 변환할 좌표계 정보 생성
		ProjCoordinate p = new ProjCoordinate();
		p.x = 126.9784034;
		p.y = 37.5665972;

		// 변환된 좌표를 담을 객체 생성
		ProjCoordinate p2 = new ProjCoordinate();

		CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
		// 변환 시스템 지정. (원본 시스템, 변환 시스템)
		CoordinateTransform coordinateTransform = ctFactory.createTransform(wgs84System, utmkSystem);
		// 좌표 변환
		ProjCoordinate projCoordinate = coordinateTransform.transform(p, p2);

		// 변환된 좌표
		double x = projCoordinate.x;
		double y = projCoordinate.y;
		
		return projCoordinate;
	}
}
