package org.activiti.image;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;

public interface ProcessDiagramGenerator {

  public InputStream generateDiagram(BpmnModel bpmnModel, List<String> highLightedActivities, List<String> highLightedFlows, List<String> activeActivityIds,List<Map<String, Object>> turnTransitionActivity);
  
  public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities, List<String> highLightedFlows, 
      String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader, double scaleFactor);
  
  public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities, List<String> highLightedFlows);
  
  public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities, 
      List<String> highLightedFlows, double scaleFactor);

  public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities);
  
  public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities, double scaleFactor);
  
  public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader);
  
  public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, String activityFontName, 
      String labelFontName, String annotationFontName, ClassLoader customClassLoader, double scaleFactor);

  public InputStream generatePngDiagram(BpmnModel bpmnModel);
  
  public InputStream generatePngDiagram(BpmnModel bpmnModel, double scaleFactor);

  public InputStream generateJpgDiagram(BpmnModel bpmnModel);
  
  public InputStream generateJpgDiagram(BpmnModel bpmnModel, double scaleFactor);
  
  public BufferedImage generatePngImage(BpmnModel bpmnModel, double scaleFactor);

}
