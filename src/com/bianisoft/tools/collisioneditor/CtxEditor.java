/* This file is part of the Bianisoft Collision Editor Tool.
 *
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *----------------------------------------------------------------------
 * Copyright (C) Alain Petit - alainpetit21@hotmail.com
 *
 * 18/12/10			0.1 First beta initial Version.
 * 12/09/11			0.1.1 Moved everything to a com.bianisoft
 *
 *-----------------------------------------------------------------------
 */
package com.bianisoft.tools.collisioneditor;


//Standard Java imports
import java.net.URL;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

//JOGL library imports
import javax.media.opengl.GL;
import com.sun.opengl.util.texture.TextureIO;

//Bianisoft imports
import com.bianisoft.engine.App;
import com.bianisoft.engine.Obj;
import com.bianisoft.engine.backgrounds.Background;
import com.bianisoft.engine.Camera;
import com.bianisoft.engine.Context;
import com.bianisoft.engine.PhysObj;
import com.bianisoft.engine.manager.physic.Line;


public class CtxEditor extends Context{
	public double m_zoomFactor= 1.0;
	public double m_nBasePosX= 0.0;
	public double m_nBasePosY= 0.0;

	public MyLine		m_lineSelected= null;
	public MyPolyline	m_polylineSelected= null;
	public MyPolygon	m_polygonSelected= null;
	public MyPoint		m_pointSelected= null;

	public ArrayList<MyLine>		m_arMyLines		= new ArrayList<MyLine>();
	public ArrayList<MyPolyline>	m_arMyPolylines	= new ArrayList<MyPolyline>();
	public ArrayList<MyPolygon>		m_arMyPolygons	= new ArrayList<MyPolygon>();

	public CtxEditor(){
		super();
	}
	
	public void checkForLoadingPending(){
		ArrayList<PhysObj>	vecPhysObj= getVectorChilds();

		for(Obj obj : vecPhysObj){
			if(obj.isKindOf(IDCLASS_Background)){
				Background backObj= (Background)obj;

				if(backObj.m_image == null){
					try {
						backObj.m_image = TextureIO.newTexture(new URL(backObj.m_stResImage), false, null);
						backObj.m_nWidth = backObj.m_image.getImageWidth();
						backObj.m_nHeight = backObj.m_image.getImageHeight();
					} catch (Exception ex) {
						System.out.printf("Error while loading:%s", "");
						System.exit(1);
					}
				}
			}
		}
	}


	public void onSave(String p_stFilename){
		try{
			FileOutputStream file		= new FileOutputStream(p_stFilename);
			DataOutputStream dos		= new DataOutputStream(file);

			//Polygons
			dos.writeInt(m_arMyPolygons.size());
			for(MyPolygon objpolygon : m_arMyPolygons){
				dos.writeInt(objpolygon.m_arPoint.size());
				dos.writeInt(objpolygon.m_nGroup);

				for(int i= 0; i < objpolygon.m_arPoint.size(); ++i){
					MyPoint pt= objpolygon.m_arPoint.get(i);

					dos.writeInt(pt.m_nX);
					dos.writeInt(pt.m_nY);
				}
			}

			//Polylines
			dos.writeInt(m_arMyPolylines.size());
			for(MyPolyline objpolyline : m_arMyPolylines){
				dos.writeInt(objpolyline.m_arPoint.size());
				dos.writeInt(objpolyline.m_nGroup);

				for(int i= 0; i < objpolyline.m_arPoint.size(); ++i){
					MyPoint pt= objpolyline.m_arPoint.get(i);

					dos.writeInt(pt.m_nX);
					dos.writeInt(pt.m_nY);
				}
			}

			//Lines
			dos.writeInt(m_arMyLines.size());
			for(MyLine objLine : m_arMyLines){
				MyPoint pt1= objLine.getPoint(0);
				MyPoint pt2= objLine.getPoint(1);

				dos.writeInt(pt1.m_nX);		dos.writeInt(pt1.m_nY);
				dos.writeInt(pt2.m_nX);		dos.writeInt(pt2.m_nY);
				dos.writeInt(objLine.m_nGroup);
			}

			dos.close();
			file.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void onLoad(String p_stFilename){
		try{
			FileInputStream file= new FileInputStream(p_stFilename);
			DataInputStream dis	= new DataInputStream(file);

			//Polygons
			int nbPolygons= dis.readInt();
			for(int i= 0; i < nbPolygons; ++i){
				MyPolygon	newPolygon= new MyPolygon();

				int nbPoints= dis.readInt();
				newPolygon.m_nGroup= dis.readInt();

				for(int j= 0; j < nbPoints; ++j){
					int	ptX		= dis.readInt();
					int	ptY		= dis.readInt();

					newPolygon.addPoint(ptX, ptY);
				}

				m_arMyPolygons.add(newPolygon);
			}


			//Polylines
			int nbPolylines= dis.readInt();
			for(int i= 0; i < nbPolylines; ++i){
				MyPolyline	newPolyline= new MyPolyline();

				int nbPoints= dis.readInt();
				newPolyline.m_nGroup= dis.readInt();

				for(int j= 0; j < nbPoints; ++j){
					int	ptX		= dis.readInt();
					int	ptY		= dis.readInt();

					newPolyline.addPoint(ptX, ptY);
				}

				m_arMyPolylines.add(newPolyline);
			}

			//Lines
			int nbLines= dis.readInt();
			for(int i= 0; i < nbLines; ++i){
				int	sX		= dis.readInt();
				int	sY		= dis.readInt();
				int	eX		= dis.readInt();
				int	eY		= dis.readInt();
				int	group	= dis.readInt();

				MyLine newLine= new MyLine(sX, sY, eX, eY);
				newLine.m_nGroup= group;

				m_arMyLines.add(newLine);
			}

			dis.close();
			file.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void quantizePointPair(double[] p_pt1, double[] p_pt2){
		double dx= p_pt1[0] - p_pt2[0];
		double dy= p_pt1[1] - p_pt2[1];
		double dxHalf= dx/2;
		double dyHalf= dy/2;
		double dist= dx*dx+dy*dy;

		if((dist < 4.0) && (dist > 0.0)){
			p_pt1[0]+= dxHalf;
			p_pt1[1]+= dyHalf;
			p_pt2[0]= p_pt1[0];
			p_pt2[1]= p_pt1[1];
		}
	}

	public void quantize(){
		int i,j;

		for(Line objLine1 : getPhysic().m_arLines){
			for(Line objLine2 : getPhysic().m_arLines){
				quantizePointPair(objLine1.m_fStart, objLine2.m_fStart);
				quantizePointPair(objLine1.m_fStart, objLine2.m_fEnd);
				quantizePointPair(objLine1.m_fEnd, objLine2.m_fStart);
				quantizePointPair(objLine1.m_fEnd, objLine2.m_fEnd);
			}
		}
	}

	public void deSelectAll(){
		m_lineSelected		= null;
		m_polylineSelected	= null;
		m_polygonSelected	= null;
		m_pointSelected		= null;
	}

	public MyLine getSelection(){
		if(m_lineSelected != null)
			return m_lineSelected;
		else if(m_polylineSelected != null)
			return m_polylineSelected;
		else if(m_polygonSelected != null)
			return m_polygonSelected;

		return null;
	}
	
	
	/***********Polygons**************/
	public boolean onPolygonSelectStart(int p_nX, int p_nY){
		double	nearestDst		= 1000000;
		boolean	ret= false;

		for(MyPolygon objpolygon : m_arMyPolygons){
			for(MyPoint pt : objpolygon.m_arPoint){
				double dx= p_nX - pt.m_nX;
				double dy= p_nY - pt.m_nY;
				double dst= dx*dx+dy*dy;

				if(dst < nearestDst){
					nearestDst			= dst;
					m_polygonSelected	= objpolygon;
					m_pointSelected		= pt;
					ret= true;
				}
			}
		}

		return ret;
	}

	public void onPolygonSelectDo(int p_nX, int p_nY){
		onLineSelectDo(p_nX, p_nY);
	}

	public void onPolygonSelectEnd(int p_nX, int p_nY){
		onLineSelectEnd(p_nX, p_nY);
	}

	public void onPolygonAddStart(int p_nX, int p_nY){
		m_polygonSelected= new MyPolygon(p_nX, p_nY);
		m_pointSelected= m_polygonSelected.addPoint(new MyPoint(p_nX, p_nY));
	}

	public void onPolygonAddDo(int p_nX, int p_nY){
		onLineAddDo(p_nX, p_nY);
	}

	public void onPolygonAddMark(int p_nX, int p_nY){
		m_pointSelected.m_nX= p_nX;
		m_pointSelected.m_nY= p_nY;
		m_pointSelected= m_polygonSelected.addPoint(new MyPoint(p_nX, p_nY));
	}

	public void onPolygonAddEnd(int p_nX, int p_nY){
		m_pointSelected.m_nX= p_nX;
		m_pointSelected.m_nY= p_nY;
		m_arMyPolygons.add(m_polygonSelected);
	}

	public void onPolygonDelete(int p_nX, int p_nY){
		onPolygonSelectStart(p_nX, p_nY);
		m_arMyPolygons.remove(m_polygonSelected);
		m_polygonSelected	= null;
		m_pointSelected	= null;
	}


	/***********PolyLines**************/
	public boolean onPolylineSelectStart(int p_nX, int p_nY){
		double	nearestDst		= 1000000;
		boolean	ret= false;

		for(MyPolyline objpolyline : m_arMyPolylines){
			for(MyPoint pt : objpolyline.m_arPoint){
				double dx= p_nX - pt.m_nX;
				double dy= p_nY - pt.m_nY;
				double dst= dx*dx+dy*dy;

				if(dst < nearestDst){
					nearestDst			= dst;
					m_polylineSelected	= objpolyline;
					m_pointSelected		= pt;
					ret= true;
				}
			}
		}

		return ret;
	}

	public void onPolylineSelectDo(int p_nX, int p_nY){
		onLineSelectDo(p_nX, p_nY);
	}

	public void onPolylineSelectEnd(int p_nX, int p_nY){
		onLineSelectEnd(p_nX, p_nY);
	}

	public void onPolylineAddStart(int p_nX, int p_nY){
		m_polylineSelected= new MyPolyline(p_nX, p_nY);
		m_pointSelected= m_polylineSelected.addPoint(new MyPoint(p_nX, p_nY));
	}

	public void onPolylineAddDo(int p_nX, int p_nY){
		onLineAddDo(p_nX, p_nY);
	}

	public void onPolylineAddMark(int p_nX, int p_nY){
		m_pointSelected.m_nX= p_nX;
		m_pointSelected.m_nY= p_nY;
		m_pointSelected= m_polylineSelected.addPoint(new MyPoint(p_nX, p_nY));
	}

	public void onPolylineAddEnd(int p_nX, int p_nY){
		m_pointSelected.m_nX= p_nX;
		m_pointSelected.m_nY= p_nY;
		m_arMyPolylines.add(m_polylineSelected);
	}

	public void onPolylineDelete(int p_nX, int p_nY){
		onPolylineSelectStart(p_nX, p_nY);
		m_arMyPolylines.remove(m_polylineSelected);
		m_polylineSelected	= null;
		m_pointSelected	= null;
	}


	/***********Lines**************/
	public boolean onLineSelectStart(int p_nX, int p_nY){
		double	nearestDst		= 1000000;
		boolean	ret= false;

		for(MyLine objLine : m_arMyLines){
			double dx, dy, dst;
			MyPoint pt= objLine.getPoint(0);
			dx= p_nX - pt.m_nX;
			dy= p_nY - pt.m_nY;
			dst= dx*dx+dy*dy;

			if(dst < nearestDst){
				nearestDst		= dst;
				m_lineSelected	= objLine;
				m_pointSelected	= pt;
				ret= true;
			}

			pt= objLine.getPoint(1);
			dx= p_nX - pt.m_nX;
			dy= p_nY - pt.m_nY;
			dst= dx*dx+dy*dy;

			if(dst < nearestDst){
				nearestDst		= dst;
				m_lineSelected	= objLine;
				m_pointSelected	= pt;
				ret= true;
			}
		}

		return ret;
	}

	public void onLineSelectDo(int p_nX, int p_nY){
		onLineSelectEnd(p_nX, p_nY);
	}

	public void onLineSelectEnd(int p_nX, int p_nY){
		if(m_pointSelected == null)	return;

		m_pointSelected.m_nX= p_nX;
		m_pointSelected.m_nY= p_nY;
	}

	public void onLineAddStart(int p_nX, int p_nY){
		m_lineSelected= new MyLine(p_nX, p_nY);
		m_pointSelected= m_lineSelected.getPoint(1);
	}

	public void onLineAddDo(int p_nX, int p_nY){
		m_pointSelected.m_nX= p_nX;
		m_pointSelected.m_nY= p_nY;
	}

	public void onLineAddEnd(int p_nX, int p_nY){
		m_pointSelected.m_nX= p_nX;
		m_pointSelected.m_nY= p_nY;
		m_arMyLines.add(m_lineSelected);
	}

	public void onLineDelete(int p_nX, int p_nY){
		onLineSelectStart(p_nX, p_nY);
		m_arMyLines.remove(m_lineSelected);
		m_lineSelected	= null;
		m_pointSelected	= null;
	}

	public void onPanStart(){
		Camera cam= Camera.getCur(Camera.TYPE_2D);
		m_nBasePosX= (int)cam.m_vPos[0];
		m_nBasePosY= (int)cam.m_vPos[1];
	}

	public void onPanDo(int p_nDifX, int p_nDifY){
		Camera cam= Camera.getCur(Camera.TYPE_2D);
		cam.setPos(m_nBasePosX + p_nDifX, m_nBasePosY + p_nDifY);
	}

	public void manage(double f){
		checkForLoadingPending();
		super.manage(f);
	}

	/**********Drawing Procedures************/
	public void drawPoint(int p_nX, int p_nY, double p_red, double p_green, double p_blue, double p_alpha){
		GL gl= App.g_CurrentGL;

		gl.glColor4d(p_red, p_green, p_blue, p_alpha);
		gl.glBegin(GL.GL_POINT);
			gl.glVertex2i(p_nX, p_nY);
		gl.glEnd();

	}

	public void drawPoint(MyPoint p_pt1, double p_red, double p_green, double p_blue, double p_alpha){
		drawPoint(p_pt1.m_nX, p_pt1.m_nY, p_red, p_green, p_blue, p_alpha);
	}

	public void drawLine(int p_nX1, int p_nY1, int p_nX2, int p_nY2, double p_red, double p_green, double p_blue, double p_alpha){
		GL gl= App.g_CurrentGL;
		
		gl.glColor4d(p_red, p_green, p_blue, p_alpha);
		gl.glBegin(GL.GL_LINE);
			gl.glVertex2i(p_nX1, p_nY1);
			gl.glVertex2i(p_nX2, p_nY2);
		gl.glEnd();
	}

	public void drawLine(MyPoint p_pt1, MyPoint p_pt2, double p_red, double p_green, double p_blue, double p_alpha){
		drawLine(p_pt1.m_nX, p_pt1.m_nY, p_pt2.m_nX, p_pt2.m_nY, p_red, p_green, p_blue, p_alpha);
	}

	public void drawNormalLineOf(MyPoint p_pt1, MyPoint p_pt2, double p_red, double p_green, double p_blue, double p_alpha){
		double	angle=	Math.atan2(p_pt1.m_nY - p_pt2.m_nY, p_pt1.m_nX - p_pt2.m_nX);
		MyPoint	ptMiddle= new MyPoint(p_pt1.m_nX + ((p_pt2.m_nX - p_pt1.m_nX)/2), p_pt1.m_nY + ((p_pt2.m_nY - p_pt1.m_nY)/2));
		MyPoint	ptNormal= new MyPoint(ptMiddle.m_nX-(Math.sin(angle)*10), ptMiddle.m_nY+(Math.cos(angle)*10));

		drawLine(ptMiddle, ptNormal, p_red, p_green, p_blue, p_alpha);
	}

	public void drawLinesArray(){
		for(MyLine objLine : m_arMyLines){
			MyPoint pt1= objLine.getPoint(0);
			MyPoint pt2= objLine.getPoint(1);
			
			drawLine(pt1, pt2, 1, 0, 0, 1);
			drawNormalLineOf(pt1, pt2, 0, 1, 0, 1);
		}
	}
	public void drawPolylinesArray(){
		for(MyPolyline objpolyline : m_arMyPolylines){
			for(int i= 1; i < objpolyline.m_arPoint.size(); ++i){
				MyPoint pt1= objpolyline.m_arPoint.get(i-1);
				MyPoint pt2= objpolyline.m_arPoint.get(i);

				drawLine(pt1, pt2, 1, 0, 0, 1);
				drawNormalLineOf(pt1, pt2, 0, 1, 0, 1);
			}
		}
	}
	public void drawPolygonsArray(){
		for(MyPolygon objpolygon : m_arMyPolygons){
			for(int i= 1; i < objpolygon.m_arPoint.size(); ++i){
				MyPoint pt1= objpolygon.m_arPoint.get(i-1);
				MyPoint pt2= objpolygon.m_arPoint.get(i);

				drawLine(pt1, pt2, 1, 0, 0, 1);
				drawNormalLineOf(pt1, pt2, 0, 1, 0, 1);
			}

			//Close the polygon
			MyPoint pt1= objpolygon.m_arPoint.get(objpolygon.m_arPoint.size() -1);
			MyPoint pt2= objpolygon.m_arPoint.get(0);

			drawLine(pt1, pt2, 1, 0, 0, 1);
			drawNormalLineOf(pt1, pt2, 0, 1, 0, 1);
		}
	}
	
	public void draw(){
		checkForLoadingPending();
		super.draw();

		App.g_theApp.orthogonalStart(App.g_CurrentDrawable);
		GL gl= App.g_CurrentGL;

		gl.glPushMatrix();

		Camera.getCur(Camera.TYPE_2D).doProjection();

		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_LINE);

		
		drawLinesArray();
		drawPolylinesArray();
		drawPolygonsArray();
				
		if(m_lineSelected != null){
			//Draw the Selected line in light red color
			MyPoint pt1= m_lineSelected.getPoint(0);
			MyPoint pt2= m_lineSelected.getPoint(1);
			drawLine(pt1, pt2, 1.0, 0.75, 0.75, 1);

			//Draw the Selected line Normal in light green color
			drawNormalLineOf(pt1, pt2, 0.75, 1.0, 0.75, 1);
		}else if(m_polylineSelected != null){
			for(int i= 1; i < m_polylineSelected.m_arPoint.size(); ++i){
				MyPoint pt1= m_polylineSelected.m_arPoint.get(i-1);
				MyPoint pt2= m_polylineSelected.m_arPoint.get(i);

				drawLine(pt1, pt2, 1.0, 0.75, 0.75, 1);
				drawNormalLineOf(pt1, pt2, 0.75, 1.0, 0.75, 1);
			}
		}else if(m_polygonSelected != null){
			for(int i= 1; i < m_polygonSelected.m_arPoint.size(); ++i){
				MyPoint pt1= m_polygonSelected.m_arPoint.get(i-1);
				MyPoint pt2= m_polygonSelected.m_arPoint.get(i);

				drawLine(pt1, pt2, 1, 0.75, 0.75, 1);
				drawNormalLineOf(pt1, pt2, 0.75, 1.0, 0.75, 1);
			}

			//Close the polygon
			MyPoint pt1= m_polygonSelected.m_arPoint.get(m_polygonSelected.m_arPoint.size() -1);
			MyPoint pt2= m_polygonSelected.m_arPoint.get(0);

			drawLine(pt1, pt2, 1, 0.75, 0.75, 1);
			drawNormalLineOf(pt1, pt2, 0.75, 1.0, 0.75, 1);
		}


		//Draw the Selected point in light blue color, with Alpha
		if(m_pointSelected != null)
			drawPoint(m_pointSelected, 0.5, 0.5, 1.0, 0.5);

		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glPopMatrix();
		App.g_theApp.orthogonalEnd(App.g_CurrentDrawable);
	}
}
