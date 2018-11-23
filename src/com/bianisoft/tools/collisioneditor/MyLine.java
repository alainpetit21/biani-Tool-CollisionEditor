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
import java.util.ArrayList;


public class MyLine{
	public int					m_nbPoints	= 0;
	public ArrayList<MyPoint>	m_arPoint	= new ArrayList<MyPoint>();
	public int					m_nGroup	= 0;
	public double				m_fAngle;

	
	public MyLine()	{}
	public MyLine(int p_nX, int p_nY){
		m_arPoint.add(new MyPoint(p_nX, p_nY));	m_arPoint.add(new MyPoint(p_nX, p_nY));}
	public MyLine(int p_nX1, int p_nY1, int p_nX2, int p_nY2){
		m_arPoint.add(new MyPoint(p_nX1, p_nY1));	m_arPoint.add(new MyPoint(p_nX2, p_nY2));}

	public MyPoint getPoint(int p_nIdx)	{return m_arPoint.get(p_nIdx);}
}

