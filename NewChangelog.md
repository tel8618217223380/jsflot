
```
Version 0.4.0 Bubble- and Candle Stick charts
- Added possibility to override default chart colors.
- Updated Flotr to Revision 146.
- Added support for Bubble Charts
- Added support for Candle Stick Charts

Version 0.3.5 IE 6 and 7 Support, Chart Zooming
- Added support for IE 6 and 7.
- Ajax redraw supported by Prototype, JQuery and JBoss Richfaces expressively
- Support for Chart Zooming with Overview.
- Fixed a bug where the chart would not render if the ID and the ClientId has the same value.
- Added possibility to add a label for each data point on the chart. 

Version 0.3.0 Click Action, Improved Drag effect, valueChangeListener replaced with actionListener
- Changed the ID naming of some of the rendered DOM elements to match the JSF ID naming Scheme
- Improved the Drag effect, so that extending the standard Flotr library is not needed
- Updated Flotr to Revision 125
- JSFlot is silent unless debug="true" is set on the jsflot:resources tag.
- Added click Event. The JSFlot actionListener is called when a datapoint is clicked. 
- Removed ValueChangeListener on drag event, replaced with actionEvent
- Live Demo much improved

Version 0.2.5 JSMin, ajaxSingle, reRender
- Added JSMin to ResourceLoaderPhaseListener to automatically pack all Javascript files served from JSFlot. This effectively halves the size of all Javascript files transferred to the client. (http://inconspicuous.org/projects/jsmin/jsmin.java)
- Added ajaxSingle for single-chart ajax submit (complete form is not submittet)
- Added reRender attribute to update parts of the DOM tree, identified by the JSF ID attribute (Not the complete ClientID)

Version 0.2.2 Stacked Bars
- Added support for Stacked Bars
- Added a JS function to refresh the chart via an AJAX call. JSFlot.AJAX.RefreshChart('chartID');
- Changed the visualization for drag and drop support
- Changed Time mode to reuse Flotrs Time mode, and added a time format option. 

Version 0.2.1 Support for non-facelets
- Added Tag classes to be able to support non-facelets JSF 1.2 applications

Version 0.2.0 Drag and Drop Support
- Added support for dragging the X-axis and triggering a ValueChangeEvent with the number of X-axis values dragged. 
- Added AJAX support for valueChangeListener, as well as re-drawing of the chart based on the result of the valueChangedEvent
- Added JavaScript library blackbird Javascript library for Javascript Debugging of JSFlot.  
- Updated to Flotr revision 123.

Version 0.1.6 
- X and Y axis labels and titles can be rotated, specified in degrees.
- Fixed a bug where only /faces/* prefix and *.jsf suffix for the Faces Servlet works with loading the JavaScript Resources.  

Version 0.1.5 - Support for Clustered Bar Charts
- Added support for Clustered Bar Charts (replacing the overlay-bar charts)
- Upgraded Flotr to Revision 114
- Added support for markers both globaly and per series.
- Added support to set marker position
- Support for vertical Y-Axis Label
- Support for Crosshair in X, Y or XY directions

Version 0.1.3 - PhaseListener bugfix
- Fixed bug where the ResouceLoaderPhaseListener assumed that the Faces Servlet mapped to *.jsf. JSFlot now works with any URL-pattern where the Faces Servlet is called as a suffix (ie. *.jsf, *.faces), as well as /faces/*.  

Version 0.1.2 - Fix for Bug in FlotChartrendererData
- Fixed cast bug in FlotChartRendererData where some values were cast to the wrong type.
 
Version 0.1.1 - Initial Release
XYDataPoint.java
- The XYDataPoint is simply what it states, and holds a single set of X and Y coordinates.
XYDataList
- The XYDataList serves two purposes. First it holds a java.util.List<XYDataPoint> list of a set of XYDataPoints to represent a complete data series.
- Second it has additional fields for storing the seires label, as well as information regarding this series line and data point rendering.
XYDataSetCollection
- This class is simply a collection of XYDataList. A fully populated XYDataSetCollection holds a minimum of 1 XYDataList references.
- The jsflot:flotChart taglib expects to get a reference to a XYDataSetCollection in its Value parameter.

Data Points configuration
- Data points can be rendered as values, or as JavaScript dates.
- Each data point can be either hidden or shown on the chart. Settings are both globally (for all series), or on a per series basis.
- An optional Tooltip is available to show extra information regarding the data point that the mouse points to.
- The tooltip position can be either corner. Alternatively the tooltip can follow the mouse.
Axis Configuration
- The X and Y Axis can have an optional title.
- The values for both X and Y axis are chosen based on the data being rendered. However, custom ranges can be defined for either axis.
- By default both the X and Y axis show 5 ticks (labels). This number can be overridden by suggesting any number. JSFlot will determine the closest match automatically.
Chart Title Configuration
- Both a title and a subtitle are available, which can be shown at the same time.
Legend Configuration
- The graph can show a legend, positioned at either corner of the graph
- The legend can be arranged in any number of columns ranging from 1 to the number of series currently displayed
- Both a background color and a background color opacity can be configured
Chart Line Options
- The chart lines can optionally be filled to create an area chart. This can be configured globally, or per series.
- If data points are shown, the chart line can optionally be switched off
Chart Dimensions
- By default the chart occupies 600 by 300 pixels. Both the width and the height can be configured to any number
Pie Chart Support
Bar Chart Support
```