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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.AttributedCharacterIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JToolBar.Separator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.table.AbstractTableModel;

//JOGL library imports
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;

//Bianisoft imports
import com.bianisoft.engine.backgrounds.Background;
import com.bianisoft.engine.Camera;


class MyTableModel extends AbstractTableModel{
	public static		MyTableModel gThis;
	public CtxEditor	m_curContext;

    public String[]			m_stColumnNames = {"X", "Y"};

	public MyTableModel(){
		super();
		gThis= this;
	}
    public int getColumnCount(){
        return 2;
    }

    public int getRowCount(){
		if(m_curContext == null)
			return 0;
		
		MyLine lineSelected= m_curContext.getSelection();

		if(lineSelected == null)
			return 0;

        return lineSelected.m_arPoint.size();
    }

    public String getColumnName(int col){
        return m_stColumnNames[col];
    }

    public Object getValueAt(int row, int col){
		if(col == 0)
	        return new Integer(m_curContext.getSelection().m_arPoint.get(row).m_nX);
		else if(col == 1)
	        return new Integer(m_curContext.getSelection().m_arPoint.get(row).m_nY);

		return null;
    }

    public Class getColumnClass(int c){
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
		return true;
    }

    public void setValueAt(Object value, int row, int col) {
		if(col == 0)
	        m_curContext.getSelection().m_arPoint.get(row).m_nX= ((Integer)value);
		else if(col == 1)
	        m_curContext.getSelection().m_arPoint.get(row).m_nY= ((Integer)value);

		fireTableCellUpdated(row, col);
    }

	public void changeSelection(){
		fireTableRowsDeleted(0, getRowCount());
		fireTableRowsInserted(0, getRowCount());
	}
}


public class CollisionEditor extends JFrame {

    static {
        // When using a GLCanvas, we have to set the Popup-Menues to be HeavyWeight,
        // so they display properly on top of the GLCanvas
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
    }
    
//    private Animator animator;
	AppCollisionEditor	m_appBiani;
	CtxEditor			m_curContext;
	int					m_nEditingMode= 0;
	int					m_nEditingShapeMode= 0;
	boolean				m_isEditingActive= false;
	boolean				m_isPanningActive= false;
	MyTableModel		m_tableModel;
	String				m_curDirectory= ".";

	int m_nBasePosX;
	int m_nBasePosY;
	
    /** Creates new form MainFrame */
    public CollisionEditor() {
        initComponents();
        setTitle("Bianitool");

		m_appBiani= new AppCollisionEditor(m_gLCanvas, this);
		m_curContext= (CtxEditor)m_appBiani.m_arObj.get(0);

		// This is a workaround for the GLCanvas not adjusting its size, when the frame is resized.
        m_gLCanvas.setMinimumSize(new Dimension());
		ValidateToolbarIcons();
		m_tableModel= MyTableModel.gThis;
		m_tableModel.m_curContext= m_curContext;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        m_gLCanvas = new GLCanvas(createGLCapabilites());
        jToolBar1 = new JToolBar();
        jBtToolbarNew = new JButton();
        jButton1 = new JButton();
        jButton4 = new JButton();
        jButton2 = new JButton();
        jSeparator3 = new Separator();
        jBtModePolygon = new JButton();
        jBtModePolyline = new JButton();
        jBtModeLine = new JButton();
        jSeparator4 = new Separator();
        jBtModeSelection = new JButton();
        jBtModeAdd = new JButton();
        jBtModeRemove = new JButton();
        jSeparator5 = new Separator();
        jBtQuantize = new JButton();
        jSeparator1 = new Separator();
        jBtMove = new JButton();
        jButtonIncreaseZoom = new JButton();
        jButtonDecreaseZoom = new JButton();
        jSeparator2 = new Separator();
        jLabel4 = new JLabel();
        jTxtFld_GroupID = new JTextField();
        jBtModifyLine = new JButton();
        jScrollPane2 = new JScrollPane();
        jTbl_LinesProperties = new JTable();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        m_gLCanvas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        m_gLCanvas.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent evt) {
                onMouseWheel(evt);
            }
        });
        m_gLCanvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                onMouseDown(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                onMouseUp(evt);
            }
        });
        m_gLCanvas.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent evt) {
                onMouseMoved(evt);
            }
            public void mouseDragged(MouseEvent evt) {
                onMouseDragged(evt);
            }
        });

        jToolBar1.setRollover(true);

        jBtToolbarNew.setIcon(new ImageIcon(getClass().getResource("/com/bianisoft/tools/collisioneditor/res/iconNewFile.png"))); // NOI18N
        jBtToolbarNew.setFocusable(false);
        jBtToolbarNew.setHorizontalTextPosition(SwingConstants.CENTER);
        jBtToolbarNew.setVerticalTextPosition(SwingConstants.BOTTOM);
        jBtToolbarNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onButtonToolbarNew(evt);
            }
        });
        jToolBar1.add(jBtToolbarNew);

        jButton1.setIcon(new ImageIcon(getClass().getResource("/com/bianisoft/tools/collisioneditor/res/iconOpen.png"))); // NOI18N
        jButton1.setToolTipText("Open a collision file");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(SwingConstants.BOTTOM);
        jButton1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                onLoadButtonMouseClicked(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton4.setIcon(new ImageIcon(getClass().getResource("/com/bianisoft/tools/collisioneditor/res/iconOpenBackground.png"))); // NOI18N
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(SwingConstants.BOTTOM);
        jButton4.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                onLoadBackground(evt);
            }
        });
        jToolBar1.add(jButton4);

        jButton2.setIcon(new ImageIcon(getClass().getResource("/com/bianisoft/tools/collisioneditor/res/iconSave.png"))); // NOI18N
        jButton2.setToolTipText("Save a collision file");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(SwingConstants.BOTTOM);
        jButton2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                onSaveButtonMouseClicked(evt);
            }
        });
        jToolBar1.add(jButton2);
        jToolBar1.add(jSeparator3);

        jBtModePolygon.setIcon(new ImageIcon(getClass().getResource("/com/bianisoft/tools/collisioneditor/res/iconPolygon.png"))); // NOI18N
        jBtModePolygon.setFocusable(false);
        jBtModePolygon.setHorizontalTextPosition(SwingConstants.CENTER);
        jBtModePolygon.setVerticalTextPosition(SwingConstants.BOTTOM);
        jBtModePolygon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onButtonModePolygon(evt);
            }
        });
        jToolBar1.add(jBtModePolygon);

        jBtModePolyline.setIcon(new ImageIcon(getClass().getResource("/com/bianisoft/tools/collisioneditor/res/iconPolyline.png"))); // NOI18N
        jBtModePolyline.setFocusable(false);
        jBtModePolyline.setHorizontalTextPosition(SwingConstants.CENTER);
        jBtModePolyline.setVerticalTextPosition(SwingConstants.BOTTOM);
        jBtModePolyline.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onButtonModePolyline(evt);
            }
        });
        jToolBar1.add(jBtModePolyline);

        jBtModeLine.setIcon(new ImageIcon(getClass().getResource("/com/bianisoft/tools/collisioneditor/res/iconLines.png"))); // NOI18N
        jBtModeLine.setFocusable(false);
        jBtModeLine.setHorizontalTextPosition(SwingConstants.CENTER);
        jBtModeLine.setVerticalTextPosition(SwingConstants.BOTTOM);
        jBtModeLine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onButtonModeLine(evt);
            }
        });
        jToolBar1.add(jBtModeLine);
        jToolBar1.add(jSeparator4);

        jBtModeSelection.setIcon(new ImageIcon(getClass().getResource("/com/bianisoft/tools/collisioneditor/res/iconSelect.png"))); // NOI18N
        jBtModeSelection.setFocusable(false);
        jBtModeSelection.setHorizontalTextPosition(SwingConstants.CENTER);
        jBtModeSelection.setVerticalTextPosition(SwingConstants.BOTTOM);
        jBtModeSelection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onButtonModeSelect(evt);
            }
        });
        jToolBar1.add(jBtModeSelection);

        jBtModeAdd.setIcon(new ImageIcon(getClass().getResource("/com/bianisoft/tools/collisioneditor/res/iconPlus.png"))); // NOI18N
        jBtModeAdd.setFocusable(false);
        jBtModeAdd.setHorizontalTextPosition(SwingConstants.CENTER);
        jBtModeAdd.setVerticalTextPosition(SwingConstants.BOTTOM);
        jBtModeAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onButtonModeAdd(evt);
            }
        });
        jToolBar1.add(jBtModeAdd);

        jBtModeRemove.setIcon(new ImageIcon(getClass().getResource("/com/bianisoft/tools/collisioneditor/res/iconMinus.png"))); // NOI18N
        jBtModeRemove.setFocusable(false);
        jBtModeRemove.setHorizontalTextPosition(SwingConstants.CENTER);
        jBtModeRemove.setVerticalTextPosition(SwingConstants.BOTTOM);
        jBtModeRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onButtonModeRemove(evt);
            }
        });
        jToolBar1.add(jBtModeRemove);
        jToolBar1.add(jSeparator5);

        jBtQuantize.setIcon(new ImageIcon(getClass().getResource("/com/bianisoft/tools/collisioneditor/res/iconQuantize.png"))); // NOI18N
        jBtQuantize.setToolTipText("Quantize the lines, which mean snap the ends so they close together");
        jBtQuantize.setFocusable(false);
        jBtQuantize.setHorizontalTextPosition(SwingConstants.CENTER);
        jBtQuantize.setVerticalTextPosition(SwingConstants.BOTTOM);
        jBtQuantize.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                onQantizeButtonMouseClicked(evt);
            }
        });
        jToolBar1.add(jBtQuantize);
        jToolBar1.add(jSeparator1);

        jBtMove.setIcon(new ImageIcon(getClass().getResource("/com/bianisoft/tools/collisioneditor/res/iconPan.png"))); // NOI18N
        jBtMove.setFocusable(false);
        jBtMove.setHorizontalTextPosition(SwingConstants.CENTER);
        jBtMove.setVerticalTextPosition(SwingConstants.BOTTOM);
        jToolBar1.add(jBtMove);

        jButtonIncreaseZoom.setIcon(new ImageIcon(getClass().getResource("/com/bianisoft/tools/collisioneditor/res/iconMagnifyPlus.png"))); // NOI18N
        jButtonIncreaseZoom.setToolTipText("\"Increase Zoom Level\"");
        jButtonIncreaseZoom.setFocusable(false);
        jToolBar1.add(jButtonIncreaseZoom);

        jButtonDecreaseZoom.setIcon(new ImageIcon(getClass().getResource("/com/bianisoft/tools/collisioneditor/res/iconMagnifyMinus.png"))); // NOI18N
        jButtonDecreaseZoom.setToolTipText("\"Decrease Zoom Level\"");
        jButtonDecreaseZoom.setFocusable(false);
        jToolBar1.add(jButtonDecreaseZoom);
        jToolBar1.add(jSeparator2);

        jLabel4.setText("Group Value: ");

        jTxtFld_GroupID.setText("0");
        jTxtFld_GroupID.setPreferredSize(new Dimension(75, 25));
        jTxtFld_GroupID.addInputMethodListener(new InputMethodListener() {
            public void inputMethodTextChanged(InputMethodEvent evt) {
                onTextChangedGroupValue(evt);
            }
            public void caretPositionChanged(InputMethodEvent evt) {
            }
        });
        jTxtFld_GroupID.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                onPropertyChange(evt);
            }
        });
        jTxtFld_GroupID.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                onKeyTypeGroupValue(evt);
            }
        });

        jBtModifyLine.setText("Modify");
        jBtModifyLine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onButtonLineModify(evt);
            }
        });

        jTbl_LinesProperties.setModel(new MyTableModel());
        jTbl_LinesProperties.setMaximumSize(new Dimension(100, 32768));
        jTbl_LinesProperties.setMinimumSize(new Dimension(100, 0));
        jTbl_LinesProperties.setPreferredSize(null);
        jScrollPane2.setViewportView(jTbl_LinesProperties);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(m_gLCanvas, GroupLayout.PREFERRED_SIZE, 640, GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(jBtModifyLine, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(20, 20, 20)
                            .addComponent(jTxtFld_GroupID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addComponent(jToolBar1, GroupLayout.DEFAULT_SIZE, 1187, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(m_gLCanvas, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                            .addComponent(jTxtFld_GroupID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(jBtModifyLine)))
                .addGap(89, 89, 89))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void onMouseDragged(MouseEvent evt) {//GEN-FIRST:event_onMouseDragged
		if(m_isPanningActive)
			m_curContext.onPanDo(evt.getX() - m_nBasePosX , evt.getY() - m_nBasePosY);
		else
			onMouseMoved(evt);
	}//GEN-LAST:event_onMouseDragged

	private void onMouseDown(MouseEvent evt) {//GEN-FIRST:event_onMouseDown
		Camera cam= Camera.getCur(Camera.TYPE_2D);
		int posX= (int)cam.doUnprojectionX(evt.getX());
		int posY= (int)cam.doUnprojectionY(evt.getY());

		if(m_isPanningActive){
			m_nBasePosX= evt.getX();
			m_nBasePosY= evt.getY();
			m_curContext.onPanStart();
		}else if(!m_isEditingActive){
			if(m_nEditingShapeMode == 0){
				if(m_nEditingMode == 0){
					m_isEditingActive= m_curContext.onPolygonSelectStart(posX, posY);
				}else if(m_nEditingMode == 1){
					m_isEditingActive= true;
					m_curContext.onPolygonAddStart(posX, posY);
				}else if(m_nEditingMode == 2){
					m_curContext.onPolygonDelete(posX, posY);
				}
			}else if(m_nEditingShapeMode == 1){
				if(m_nEditingMode == 0){
					m_isEditingActive= m_curContext.onPolylineSelectStart(posX, posY);
				}else if(m_nEditingMode == 1){
					m_isEditingActive= true;
					m_curContext.onPolylineAddStart(posX, posY);
				}else if(m_nEditingMode == 2){
					m_curContext.onPolylineDelete(posX, posY);
				}
			}else if(m_nEditingShapeMode == 2){
				if(m_nEditingMode == 0){
					m_isEditingActive= m_curContext.onLineSelectStart(posX, posY);
				}else if(m_nEditingMode == 1){
					m_isEditingActive= true;
					m_curContext.onLineAddStart(posX, posY);
				}else if(m_nEditingMode == 2){
					m_curContext.onLineDelete(posX, posY);
				}
			}
		}else if(m_isEditingActive){
			if(m_nEditingShapeMode == 0){
				if(m_nEditingMode == 0){
					m_isEditingActive= false;
					m_curContext.onPolygonSelectEnd(posX, posY);
				}else if(m_nEditingMode == 1){
					if(evt.getButton() == MouseEvent.BUTTON3){
						m_isEditingActive= false;
						m_curContext.onPolygonAddEnd(posX, posY);
					}else{
						m_curContext.onPolygonAddMark(posX, posY);
					}
				}
			}else if(m_nEditingShapeMode == 1){
				if(m_nEditingMode == 0){
					m_isEditingActive= false;
					m_curContext.onPolylineSelectEnd(posX, posY);
				}else if(m_nEditingMode == 1){
					if(evt.getButton() == MouseEvent.BUTTON3){
						m_isEditingActive= false;
						m_curContext.onPolylineAddEnd(posX, posY);
					}else{
						m_curContext.onPolylineAddMark(posX, posY);
					}
				}
			}else if(m_nEditingShapeMode == 2){
				if(m_nEditingMode == 0){
					m_isEditingActive= false;
					m_curContext.onLineSelectEnd(posX, posY);
				}else if(m_nEditingMode == 1){
					m_isEditingActive= false;
					m_curContext.onLineAddEnd(posX, posY);
				}
			}
		}

		m_tableModel.changeSelection();
	}//GEN-LAST:event_onMouseDown

	private void onMouseUp(MouseEvent evt) {//GEN-FIRST:event_onMouseUp

	}//GEN-LAST:event_onMouseUp

	private void onLoadButtonMouseClicked(MouseEvent evt) {//GEN-FIRST:event_onLoadButtonMouseClicked
		JFileChooser fc= new JFileChooser(m_curDirectory);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Collision file", "col");

		fc.setFileFilter(filter);
		int returnVal= fc.showOpenDialog(this);
		if(returnVal != JFileChooser.APPROVE_OPTION)
			return;

		m_curContext.onLoad(fc.getSelectedFile().getAbsolutePath());
	}//GEN-LAST:event_onLoadButtonMouseClicked

	private void onSaveButtonMouseClicked(MouseEvent evt) {//GEN-FIRST:event_onSaveButtonMouseClicked
		JFileChooser fc= new JFileChooser(m_curDirectory);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Collision file", "col");

		fc.setFileFilter(filter);
		int returnVal= fc.showSaveDialog(this);
		if(returnVal != JFileChooser.APPROVE_OPTION)
			return;

		m_curContext.onSave(fc.getSelectedFile().getAbsolutePath());
	}//GEN-LAST:event_onSaveButtonMouseClicked

	private void onQantizeButtonMouseClicked(MouseEvent evt) {//GEN-FIRST:event_onQantizeButtonMouseClicked
		m_curContext.quantize();
	}//GEN-LAST:event_onQantizeButtonMouseClicked

	private void onLoadBackground(MouseEvent evt) {//GEN-FIRST:event_onLoadBackground
		JFileChooser fc= new JFileChooser(".");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images file", "png", "tga");

		fc.setFileFilter(filter);
		int returnVal= fc.showOpenDialog(this);
		if(returnVal != JFileChooser.APPROVE_OPTION)
			return;

		Background back= Background.create(Background.TYPE_NORMAL, null, null);
		back.m_stResImage= "file:" + fc.getSelectedFile().getAbsolutePath();
		m_appBiani.m_ctxCur.addChild(back);

	}//GEN-LAST:event_onLoadBackground

	private void onButtonLineModify(ActionEvent evt) {//GEN-FIRST:event_onButtonLineModify
		MyLine lineSel= m_curContext.getSelection();

		if(lineSel != null)
			lineSel.m_nGroup= Integer.parseInt(jTxtFld_GroupID.getText());
	}//GEN-LAST:event_onButtonLineModify

	private void ValidateToolbarIcons(){
		jBtModePolygon.setSelected(!m_isPanningActive && (m_nEditingShapeMode == 0));
		jBtModePolyline.setSelected(!m_isPanningActive && (m_nEditingShapeMode == 1));
		jBtModeLine.setSelected(!m_isPanningActive && (m_nEditingShapeMode == 2));

		jBtModeSelection.setSelected(!m_isPanningActive && (m_nEditingMode == 0));
		jBtModeAdd.setSelected(!m_isPanningActive && (m_nEditingMode == 1));
		jBtModeRemove.setSelected(!m_isPanningActive && (m_nEditingMode == 2));

		jBtMove.setSelected(m_isPanningActive);
	}

	private void onButtonModePolygon(ActionEvent evt) {//GEN-FIRST:event_onButtonModePolygon
		m_nEditingShapeMode= 0;
		m_curContext.deSelectAll();
		ValidateToolbarIcons();
	}//GEN-LAST:event_onButtonModePolygon

	private void onButtonModePolyline(ActionEvent evt) {//GEN-FIRST:event_onButtonModePolyline
		m_nEditingShapeMode= 1;
		m_curContext.deSelectAll();
		ValidateToolbarIcons();
	}//GEN-LAST:event_onButtonModePolyline

	private void onButtonModeLine(ActionEvent evt) {//GEN-FIRST:event_onButtonModeLine
		m_nEditingShapeMode= 2;
		m_curContext.deSelectAll();
		ValidateToolbarIcons();
	}//GEN-LAST:event_onButtonModeLine

	private void onButtonModeAdd(ActionEvent evt) {//GEN-FIRST:event_onButtonModeAdd
		m_nEditingMode= 1;
		ValidateToolbarIcons();
	}//GEN-LAST:event_onButtonModeAdd

	private void onButtonModeRemove(ActionEvent evt) {//GEN-FIRST:event_onButtonModeRemove
		m_nEditingMode= 2;
		ValidateToolbarIcons();
	}//GEN-LAST:event_onButtonModeRemove

	private void onButtonModeSelect(ActionEvent evt) {//GEN-FIRST:event_onButtonModeSelect
		m_nEditingMode= 0;
		ValidateToolbarIcons();
	}//GEN-LAST:event_onButtonModeSelect

	private void onMouseMoved(MouseEvent evt) {//GEN-FIRST:event_onMouseMoved
		Camera cam= Camera.getCur(Camera.TYPE_2D);
		int posX= (int)cam.doUnprojectionX(evt.getX());
		int posY= (int)cam.doUnprojectionY(evt.getY());

		m_isPanningActive= evt.isControlDown();
		ValidateToolbarIcons();

		if(m_isPanningActive){
			return;
		}else if(m_isEditingActive){
			if(m_nEditingShapeMode == 0){
				if(m_nEditingMode == 0){
					m_curContext.onPolygonSelectDo(posX, posY);
				}else if(m_nEditingMode == 1){
					m_curContext.onPolygonAddDo(posX, posY);
				}
			}else if(m_nEditingShapeMode == 1){
				if(m_nEditingMode == 0){
					m_curContext.onPolylineSelectDo(posX, posY);
				}else if(m_nEditingMode == 1){
					m_curContext.onPolylineAddDo(posX, posY);
				}
			}else if(m_nEditingShapeMode == 2){
				if(m_nEditingMode == 0){
					m_curContext.onLineSelectDo(posX, posY);
				}else if(m_nEditingMode == 1){
					m_curContext.onLineAddDo(posX, posY);
				}
			}
		}
	}//GEN-LAST:event_onMouseMoved

	private void onTextChangedGroupValue(InputMethodEvent evt) {//GEN-FIRST:event_onTextChangedGroupValue
		AttributedCharacterIterator m_nNumber= evt.getText();

		int toto= 23;
	}//GEN-LAST:event_onTextChangedGroupValue

	private void onPropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_onPropertyChange
		int toto= 23;
	}//GEN-LAST:event_onPropertyChange

	private void onKeyTypeGroupValue(KeyEvent evt) {//GEN-FIRST:event_onKeyTypeGroupValue
		int toto= 23;
		int toto2= Integer.parseInt(jTxtFld_GroupID.getText());
		int toto3= 54;
	}//GEN-LAST:event_onKeyTypeGroupValue

	private void onMouseWheel(MouseWheelEvent evt) {//GEN-FIRST:event_onMouseWheel
		Camera cam= Camera.getCur(Camera.TYPE_2D);
		cam.m_fZoom-= (double)evt.getWheelRotation() / 10.0;
	}//GEN-LAST:event_onMouseWheel

	private void onButtonToolbarNew(ActionEvent evt) {//GEN-FIRST:event_onButtonToolbarNew
		((CtxEditor)m_curContext).m_arMyLines.clear();
		((CtxEditor)m_curContext).m_arMyPolygons.clear();
		((CtxEditor)m_curContext).m_arMyPolylines.clear();
	}//GEN-LAST:event_onButtonToolbarNew

	private GLCapabilities createGLCapabilites(){
		GLCapabilities capabilities = new GLCapabilities();
		capabilities.setHardwareAccelerated(true);

		// try to enable 2x anti aliasing - should be supported on most hardware
		capabilities.setNumSamples(2);
		capabilities.setSampleBuffers(true);

		return capabilities;
    }
    
    public static void main(String args[]) {
        // Run this in the AWT event thread to prevent deadlocks and race conditions
        EventQueue.invokeLater(new Runnable(){
            public void run(){

                // switch to system l&f for native font rendering etc.
                try{
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }catch(Exception ex){
                    Logger.getLogger(getClass().getName()).log(Level.INFO, "can not enable system look and feel", ex);
                }

                CollisionEditor frame= new CollisionEditor();
                frame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jBtModeAdd;
    private JButton jBtModeLine;
    private JButton jBtModePolygon;
    private JButton jBtModePolyline;
    private JButton jBtModeRemove;
    private JButton jBtModeSelection;
    private JButton jBtModifyLine;
    private JButton jBtMove;
    private JButton jBtQuantize;
    private JButton jBtToolbarNew;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton4;
    private JButton jButtonDecreaseZoom;
    private JButton jButtonIncreaseZoom;
    private JLabel jLabel4;
    private JScrollPane jScrollPane2;
    private Separator jSeparator1;
    private Separator jSeparator2;
    private Separator jSeparator3;
    private Separator jSeparator4;
    private Separator jSeparator5;
    private JTable jTbl_LinesProperties;
    private JToolBar jToolBar1;
    private JTextField jTxtFld_GroupID;
    private GLCanvas m_gLCanvas;
    // End of variables declaration//GEN-END:variables
}
