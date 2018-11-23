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


public class MyPolyline extends MyLine{
	public MyPolyline()						{m_nbPoints= 0;	}
	public MyPolyline(int p_nX, int p_nY)	{m_nbPoints= 0;	addPoint(new MyPoint(p_nX, p_nY));}
	public MyPolyline(MyPoint p_point)		{m_nbPoints= 0;	addPoint(p_point);}

	public MyPoint addPoint(int p_nX, int p_nY){
		MyPoint pt= new MyPoint(p_nX, p_nY);

		return addPoint(pt);
	}

	public MyPoint addPoint(MyPoint p_point){
		m_nbPoints++;
		m_arPoint.add(p_point);
		
		return p_point;
	}

	public void removePoint(MyPoint p_point){
		m_nbPoints--;
		m_arPoint.remove(p_point);
	}
}

