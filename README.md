// This is a list of programs I have created

StarsAngle - calculates the angular seperation between stars of given RA and Dec

exponentialmethod - Calculates the exponential of a number via Taylor expansion method

ExtractSkyCounts - splits string copied cells from an excel file into an array and then outputs relevant Counts cells. Also does some calculations to detmine flux, magnitudes and colour (B-V). Also added a check with an error message if strings are different sizes.

CompareXmls - compares 2 databases for matching entries (within some thresholds)
1) Opens database downloaded from Aladin, and opens database create by ExtractSkyCounts.
2) Ignores all database entries without a proper motion entry
3) Ignores all stars with pm outside of the threshold of the clusters pm (10m'')
4) Takes RA & DEC of remaining aladin entries and scans through cluster database for mathcing stars withing threshold (10 pixels)
5) All matches are output to a table
