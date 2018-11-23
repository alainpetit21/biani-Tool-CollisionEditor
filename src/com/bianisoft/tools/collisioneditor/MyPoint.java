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


public class MyPoint{
	public int m_nX= 0;
	public int m_nY= 0;

	public MyPoint()							{	}
	public MyPoint(int p_nX, int p_nY)			{m_nX= p_nX; m_nY= p_nY;}
	public MyPoint(double p_fX, double p_fY)	{m_nX= (int)p_fX; m_nY= (int)p_fY;}
};

