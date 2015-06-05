# Description #
The JSFlot JSF chart library builds on top of the JavaScript Open Source Project Flotr (a javascript plotting library based on the Prototype Javascript Framework) to create stunning interactive charts purely using JavaScript. The JSFlot charting library is simple to install, easy to configure and easy to use in your custom application. All of the applications dependencies (purely JavaScript related) are included in the Jar file.

The goal of the JSFlot project is to support all the main features of Flotr (Flotr has its own project page set up at http://code.google.com/p/flotr/), while remaining easy and simple to install and use.

**Version 0.7.0 is available in the "Downloads" Tab.** Full changes are found in the [Changelog](https://bitbucket.org/joachimhs/jsflot/src/tip/JSFlot/Changelog.txt).
Be sure to check out the new Tutorial, available through the [JSFlot Live Demo Tutorial](http://jsflotdemo.haagen.name/tutorial.jsf).

**Early test with JSF 2.0.0 Beta 2 indicates that JSFlot is compatible with JSF 2 when JSF 2 becomes available.**

**Add Your Feature Requests here: [FeatureRequests](FeatureRequests.md)**

# New Features #

**Version 0.7.0**
  * Fixed bug where JSFlotChart did not draw when used inside a repeat tag ([Issue 16](https://code.google.com/p/jsflot/issues/detail?id=16) and [Issue 33](https://code.google.com/p/jsflot/issues/detail?id=33))
  * Fixed bug where the XYDataSetCollection was erased on use
  * Added rudimentary support for periodically updating the chart via AJAX (redrawInterval=seconds)
  * Added some concerrency control to the data to support multiple charts being rerendered asyncronously by the client
  * General bug fixes for stability towards 1.0

**New in Version 0.6.0**
  * Added support for ICE Faces
  * Added support for specifying width and height in percent in addition to in pixels.
  * Added support for date format in tooplip when mode is 'time'

**New in Version 0.5.0**
  * Added support for subtags XYDataList, XYDataPoint, BubbleDataPoint and CandlestickDataPoint

# Live Demonstration #
  * For a working Live Demonstration of JSFlot as well as documentation, point your browser to the [JSFlot Live Demo](http://jsflotdemo.haagen.name/index.jsf)

# Example charts #

![http://jsflotdemo.haagen.name/images/linechartexample.jpg](http://jsflotdemo.haagen.name/images/linechartexample.jpg)
![http://jsflotdemo.haagen.name/images/pieexample.jpg](http://jsflotdemo.haagen.name/images/pieexample.jpg)
![http://jsflotdemo.haagen.name/images/barchartexample.jpg](http://jsflotdemo.haagen.name/images/barchartexample.jpg)

# Main Features #

  * Support for Line, Pie, Clustered bar and Stacked bar charts
  * Support for Bubble Chart and Candle Chart
  * Line and Bar charts supports a Time mode
  * Interactive data points with tooltip
  * Legend support
  * Very attractive looks
  * Support for Crosshair
  * Drag and drop support with a ValueChangeListener that reports on the number of X-axis points that are dragged
  * Support major browsers and platforms
  * Works with most other JSF libraries, including JBoss RichFaces
  * Easy to install the frameworks and tags are easy to use in your application

# Upcoming Features #

  * More custumization of the charts
  * Support for two X and Y Axis'
  * Support for Candlestick charts

# Who develops JSFlot ? #

JSFlot is developed by Joachim Haagen Skeie. Joachim Works as an IT Architect and Software Engineer at a large norwegian IT company. You can read more about joachim on his [LinkedIn profile](http://www.linkedin.com/pub/11/893/78b).