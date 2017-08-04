//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package atu.testng.reports.writers;

import atu.testng.reports.enums.Colors;
import atu.testng.reports.enums.ExceptionDetails;
import atu.testng.reports.enums.ReportLabels;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.AuthorDetails;
import atu.testng.reports.utils.Directory;
import atu.testng.reports.utils.Platform;
import atu.testng.reports.utils.Steps;
import atu.testng.reports.utils.Utils;
import atu.testng.reports.writers.CurrentRunPageWriter;
import atu.testng.reports.writers.ReportsPage;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestCaseReportsPageWriter extends ReportsPage {
    public TestCaseReportsPageWriter() {
    }

    public static void header(PrintWriter var0, ITestResult var1) {
        var0.println("<!DOCTYPE html>\n\n<html>\n    <head>\n        <title>Pie Charts</title>\n\n        <link rel=\"stylesheet\" type=\"text/css\" href=\"../" + getTestCaseHTMLPath(var1) + "HTML_Design_Files/CSS/design.css\" />\n" + "        <link rel=\"stylesheet\" type=\"text/css\" href=\"../" + getTestCaseHTMLPath(var1) + "HTML_Design_Files/CSS/jquery.jqplot.css\" />\n" + "\n" + "        <script type=\"text/javascript\" src=\"../" + getTestCaseHTMLPath(var1) + "HTML_Design_Files/JS/jquery.min.js\"></script>\n" + "        <script type=\"text/javascript\" src=\"../" + getTestCaseHTMLPath(var1) + "HTML_Design_Files/JS/jquery.jqplot.min.js\"></script>\n" + "        <!--[if lt IE 9]>\n" + "        <script language=\"javascript\" type=\"text/javascript\" src=\"../" + getTestCaseHTMLPath(var1) + "HTML_Design_Files/JS/excanvas.js\"></script>\n" + "        <![endif]-->\n" + "\n" + "        <script language=\"javascript\" type=\"text/javascript\" src=\"../" + getTestCaseHTMLPath(var1) + "HTML_Design_Files/JS/jqplot.pieRenderer.min.js\"></script>\n" + "        <script language=\"javascript\" type=\"text/javascript\">" + "$(document).ready(function() {" + " $(\".exception\").hide();" + " $(\"#showmenu\").show();" + " $(\'#showmenu\').click(function() {" + " $(\'.exception\').toggle(\"slide\");" + " });" + " });" + "        </script>" + "    </head>\n" + "    <body>\n" + "        <table id=\"mainTable\">\n" + "            <tr id=\"header\" >\n" + "                <td id=\"logo\">" + "<img src=\"../" + getTestCaseHTMLPath(var1) + "HTML_Design_Files/IMG/" + ReportLabels.ATU_LOGO.getLabel() + "\" alt=\"Logo\" height=\"80\" width=\"140\" /> " + "<br/>" + ReportLabels.ATU_CAPTION.getLabel() + "</td>\n" + "                <td id=\"headertext\">\n" + "           " + ReportLabels.HEADER_TEXT.getLabel() + "         \n" + "<div style=\"padding-right:20px;float:right\"><img src=\"../" + getTestCaseHTMLPath(var1) + "HTML_Design_Files/IMG/" + ReportLabels.PROJ_LOGO.getLabel() + "\" height=\"70\" width=\"140\" /> </i></div>" + "                </td>\n" + "            </tr>");
    }

    public static String getExecutionTime(ITestResult var0) {
        long var1 = var0.getEndMillis() - var0.getStartMillis();
        if(var1 > 1000L) {
            var1 /= 1000L;
            return var1 + " Sec";
        } else {
            return var1 + " Milli Sec";
        }
    }

    private static String getExceptionDetails(ITestResult var0) {
        try {
            var0.getThrowable().toString();
        } catch (Throwable var4) {
            return "";
        }

        String var1 = var0.getThrowable().toString();
        String var2 = var1;
        if(var1.contains(":")) {
            var2 = var1.substring(0, var1.indexOf(":")).trim();
        } else {
            var1 = "";
        }

        try {
            var2 = getExceptionClassName(var2, var1);
            if(var2.equals("Assertion Error")) {
                if(var1.contains(">")) {
                    var2 = var2 + var1.substring(var1.indexOf(":"), var1.lastIndexOf(">") + 1).replace(">", "\"");
                    var2 = var2.replace("<", "\"");
                }

                if(var0.getThrowable().getMessage().trim().length() > 0) {
                    var2 = var0.getThrowable().getMessage().trim();
                }
            } else if(var1.contains("{")) {
                var2 = var2 + var1.substring(var1.indexOf("{"), var1.lastIndexOf("}"));
                var2 = var2.replace("{\"method\":", " With ").replace(",\"selector\":", " = ");
            } else if(var2.equals("Unable to connect Browser") && var1.contains(".")) {
                var2 = var2 + ": Browser is Closed";
            } else if(var2.equals("WebDriver Exception")) {
                var2 = var0.getThrowable().getMessage();
            }
        } catch (ClassNotFoundException var5) {
            ;
        } catch (Exception var6) {
            ;
        }

        var2 = var2.replace(">", "\"");
        var2 = var2.replace("<", "\"");
        return var2;
    }

    private static String getExceptionClassName(String var0, String var1) throws ClassNotFoundException {
        String var2 = "";

        try {
            var2 = ExceptionDetails.valueOf(var0.trim().replace(".", "_")).getExceptionInfo();
        } catch (Exception var4) {
            var2 = var0;
        }

        return var2;
    }

    public static String getReqCoverageInfo(ITestResult var0) {
        try {
            return var0.getAttribute("reqCoverage") == null?ReportLabels.TC_INFO_LABEL.getLabel():var0.getAttribute("reqCoverage").toString();
        } catch (Exception var2) {
            return ReportLabels.TC_INFO_LABEL.getLabel();
        }
    }

    public static void content(PrintWriter var0, ITestResult var1, int var2) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        var0.println("<td id=\"content\">   \n                    <div class=\"info\">\n                        The following table lists down the Sequential Steps during the Run <br/>\nTestCase Name: <b>" + var1.getName() + "  :  Iteration " + var1.getAttribute("iteration").toString() + "</b><br/>" + "                        Time Taken for Executing: <b>" + getExecutionTime(var1) + "</b> <br/>\n" + "                        Current Run Number: <b>Run " + var2 + "</b></br>\n" + "Method Type: <b>" + CurrentRunPageWriter.getMethodType(var1) + "</b></br>" + "                    </div>");
        var0.println("<div class=\"info\"><br/><b>Requirement Coverage/ TestCase Description</b><br/><br/>" + getReqCoverageInfo(var1) + "</div>");
        var0.println("<div class=\"chartStyle summary\" style=\"background-color: #646D7E;width: 30%; height: 200px;\">          \n                        <b>Execution Platform Details</b><br/><br/>\n                        <table>\n                            <tr>\n                                <td>O.S</td>\n                                <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td>\n                                <td>" + Platform.OS + ", " + Platform.OS_ARCH + "Bit, v" + Platform.OS_VERSION + "</td>\n" + "                            </tr>\n" + "                            <tr>\n" + "                                <td>Java</td>\n" + "                                <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td>\n" + "                                <td>" + Platform.JAVA_VERSION + "</td>\n" + "                            </tr>\n" + "\n" + "                            <tr>\n" + "                                <td>Hostname</td>\n" + "                                <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td>\n" + "                                <td>" + Platform.getHostName() + "</td>\n" + "                            </tr>\n" + "\n" + "                            <tr>\n" + "                                <td>Selenium</td>\n" + "                                <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td>\n" + "                                <td>" + Platform.DRIVER_VERSION + "</td>\n" + "                            </tr>\n" + "                        </table>  \n" + "                    </div>\n" + "                   ");
        var0.println(" <div class=\"chartStyle summary\" style=\"background-color: " + getColorBasedOnResult(var1) + ";margin-left: 20px; height: 200px;width: 30%; \">\n" + "                        <b>Summary</b><br/><br/>\n" + "                        <table>\n" + "                            <tr>\n" + "                                <td>Status</td>\n" + "                                <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td>\n" + "                                <td>" + getResult(var1) + "</td>\n" + "                            </tr>\n" + "                            <tr>\n" + "                                <td>Execution Date</td>\n" + "                                <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td>\n" + "                                <td>" + strDate + "</td>\n" + "                            </tr>\n" + "                            \n" + "\n" + "                            <tr>\n" + "                                <td>Browser</td>\n" + "                                <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td>\n" + "                                <td>" + getBrowserName(var1) + "," + getBrowserVersion(var1) + "</td>\n" + "                            </tr>\n" + "                        </table> \n" + "                    </div>");
        AuthorDetails var3 = null;

        try {
            if(var1.getAttribute("authorInfo") == null) {
                var3 = new AuthorDetails();
            } else {
                var3 = (AuthorDetails)var1.getAttribute("authorInfo");
            }
        } catch (Exception var13) {
            ;
        }

        var0.println(" <div class=\"chartStyle summary\" style=\"background-color: #7F525D;margin-left: 20px; height: 200px;width: 30%; \">\n                       <b>Author Info</b><br/><br/>\n                        <table>\n                            <tr>\n                                <td>Author Name</td>\n                                <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td>\n                                <td>" + var3.getAuthorName() + "</td>                                \n" + "                            </tr>\n" + "                            <tr>\n" + "                                <td>Creation Date</td>\n" + "                                <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td>\n" + "                                <td>" + var3.getCreationDate() + "</td>\n" + "                            </tr>\n" + "                            <tr>\n" + "                                <td>Version</td>\n" + "                                <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td>\n" + "                                <td>" + var3.getVersion() + "</td>\n" + "                            </tr>\n" + "                            <tr>\n" + "                                <td>System User</td>\n" + "                                <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td>\n" + "                                <td>" + Platform.USER + "</td>\n" + "                            </tr>\n" + "                        </table> \n" + "                    </div>\n" + "                    ");
        int var16;
        if(var1.getStatus() != 3) {
            List var4 = Reporter.getOutput(var1);
            var0.println("   <div>\n                        <table class=\"chartStyle\" id=\"tableStyle\" style=\"height:50px; float: left\">\n                            <tr>\n                                <th>S.No</th>\n                                <th>Step Description</th>\n                                <th>Input Value</th>\n                                <th>Extended Screenshot</th>\n                                <th>Actual Value</th>\n                                <th>Time</th>\n                                <th>Line No</th>\n                                <th>Status</th>\n                                <th>Screen shot</th>\n                            </tr>\n                           \n");
            boolean var5 = true;
            if(Reporter.getOutput(var1).size() <= 0) {
                var0.print("<tr>");
                var0.print("<td colspan=\"8\"><b>No Steps Available</b></td>");
                var0.print("</tr>");
            }

            var16 = 1;
            Iterator var6 = var4.iterator();

            while(var6.hasNext()) {
                String var7 = (String)var6.next();
                Steps var8 = null;
                var8 = (Steps)var1.getAttribute(var7);
                if(var8 == null) {
                    var0.print("<tr>");
                    var0.println("<td>" + var16 + "</td>");
                    var0.print("<td style=\"text-align:left\" colspan=\"8\">" + var7 + "</td></tr>");
                    ++var16;
                } else {
                    var0.print("<tr>");
                    var0.println("<td>" + var16 + "</td>");
                    var0.println("<td>" + var8.getDescription() + "</td>");
                    var0.println("<td>" + var8.getInputValue() + "</td>");
                    var0.println("<td>" + var8.getExpectedValue() + "</td>");
                    var0.println("<td>" + var8.getActualValue() + "</td>");
                    var0.println("<td>" + var8.getTime() + "</td>");
                    var0.println("<td>" + var8.getLineNum() + "</td>");
                    var0.println("<td>" + getLogDescription(var8.getLogAs(), var1) + "</td>");

                    try {
                        Integer.parseInt(var8.getScreenShot().trim());
                        var0.println("<td><a href=\"img/" + var16 + ".PNG" + "\"><img alt=\"No Screenshot\" src=\"img/" + var16 + ".PNG" + "\"/></a></td>");
                    } catch (Exception var12) {
                        var0.println("<td></td>");
                    }

                    var0.print("</tr>");
                    ++var16;
                }
            }

            var0.print("\n                        </table>  \n");
        }

        if(var1.getParameters().length > 0 || var1.getStatus() == 3 || var1.getStatus() == 2) {
            var0.println(" <div class=\"chartStyle summary\" style=\"color: black;width: 98%; height: 100%; padding-bottom: 30px;\">          \n");
            if(var1.getParameters().length > 0) {
                var0.print("<b>Parameters: </b><br/>");
                Object[] var14 = var1.getParameters();
                var16 = var14.length;

                for(int var17 = 0; var17 < var16; ++var17) {
                    Object var20 = var14[var17];
                    var0.print("Param: " + var20.toString() + "<br/>");
                }
            }

            if(var1.getStatus() == 3) {
                var0.print("<br/><br/>");
                var0.println("                      <b>Reason for Skipping</b><br/><br/>\n");
                String[] var15 = var1.getMethod().getGroupsDependedUpon();
                String[] var18 = var1.getMethod().getMethodsDependedUpon();
                int var9;
                String var10;
                String var19;
                String[] var21;
                int var22;
                if(var15.length > 0) {
                    var19 = "";
                    var21 = var15;
                    var22 = var15.length;

                    for(var9 = 0; var9 < var22; ++var9) {
                        var10 = var21[var9];
                        var19 = var19 + var10 + "<br/>";
                    }

                    var0.print("<b>Depends on Groups: </b><br/>" + var19);
                }

                if(var18.length > 0) {
                    var19 = "";
                    var21 = var18;
                    var22 = var18.length;

                    for(var9 = 0; var9 < var22; ++var9) {
                        var10 = var21[var9];
                        var19 = var19 + var10 + "<br/>";
                    }

                    var0.print("<b>Depends on Methods: </b><br/>" + var19 + "<br/><br/>");
                }

                var0.print("                    </div>");
            }

            if(var1.getStatus() == 2) {
                var0.println("                        <br/><br/><b>Reason for Failure:&nbsp;&nbsp;</b>" + getExceptionDetails(var1) + "<br/><br/>\n" + "<b id=\"showmenu\">Click Me to Show/Hide the Full Stack Trace</b>" + "<div class=\"exception\">");

                try {
                    var1.getThrowable().printStackTrace(var0);
                } catch (Exception var11) {
                    ;
                }

                var0.print("</div>");
            }

            var0.print("                    </div>");
        }

        var0.print("                    </div>\n\n                </td>\n            </tr>");
    }

    public static String getLogDescription(LogAs var0, ITestResult var1) {
        switch(TestCaseReportsPageWriter.SyntheticClass_1.$SwitchMap$atu$testng$reports$logging$LogAs[var0.ordinal()]) {
            case 1:
                return "<img style=\"height:20px;width:20px;border:none\"  alt=\"PASS\" src=\"../" + getTestCaseHTMLPath(var1) + "/HTML_Design_Files/IMG/logpass.png\" />";
            case 2:
                return "<img style=\"height:18px;width:18px;border:none\"  alt=\"FAIL\" src=\"../" + getTestCaseHTMLPath(var1) + "/HTML_Design_Files/IMG/logfail.png\" />";
            case 3:
                return "<img style=\"height:20px;width:20px;border:none\" alt=\"INFO\" src=\"../" + getTestCaseHTMLPath(var1) + "/HTML_Design_Files/IMG/loginfo.png\" />";
            case 4:
                return "<img style=\"height:20px;width:20px;border:none\"  alt=\"WARNING\" src=\"../" + getTestCaseHTMLPath(var1) + "/HTML_Design_Files/IMG/logwarning.png\" />";
            default:
                return "img";
        }
    }

    public static String getSkippedDescription(ITestResult var0) {
        String[] var1 = var0.getMethod().getGroupsDependedUpon();
        String[] var2 = var0.getMethod().getMethodsDependedUpon();
        String var3 = "";
        String[] var4 = var1;
        int var5 = var1.length;

        int var6;
        for(var6 = 0; var6 < var5; ++var6) {
            String var7 = var4[var6];
            var3 = var3 + var7;
        }

        String var9 = "";
        String[] var10 = var2;
        var6 = var2.length;

        for(int var11 = 0; var11 < var6; ++var11) {
            String var8 = var10[var11];
            var9 = var9 + var8;
        }

        return "";
    }

    private static String getBrowserName(ITestResult var0) {
        try {
            return var0.getAttribute(Platform.BROWSER_NAME_PROP).toString();
        } catch (Exception var2) {
            return "";
        }
    }

    private static String getBrowserVersion(ITestResult var0) {
        try {
            return var0.getAttribute(Platform.BROWSER_VERSION_PROP).toString();
        } catch (Exception var2) {
            return "";
        }
    }

    private static String getColorBasedOnResult(ITestResult var0) {
        return var0.getStatus() == 1?Colors.PASS.getColor():(var0.getStatus() == 2?Colors.FAIL.getColor():(var0.getStatus() == 3?Colors.SKIP.getColor():Colors.PASS.getColor()));
    }

    private static String getResult(ITestResult var0) {
        if(var0.getStatus() == 1) {
            try {
                return var0.getAttribute("passedButFailed").equals("passedButFailed")?"Failed":"Passed";
            } catch (Exception var2) {
                return "Passed";
            }
        } else {
            return var0.getStatus() == 2?"Failed":(var0.getStatus() == 3?"Skipped":"Unknown");
        }
    }

    public static String getTestCaseHTMLPath(ITestResult var0) {
        String var1 = var0.getAttribute("reportDir").toString();
        var1 = var1.substring(var1.indexOf(Directory.RESULTSDir) + Directory.RESULTSDir.length() + 1);
        String[] var2 = var1.replace(Directory.SEP, "##@##@##").split("##@##@##");
        var1 = "";

        for(int var3 = 0; var3 < var2.length; ++var3) {
            var1 = var1 + "../";
        }

        return var1;
    }

    public static void menuLink(PrintWriter var0, ITestResult var1, int var2) {
        getTestCaseHTMLPath(var1);
        var0.println("\n            <tr id=\"container\">\n                <td id=\"menu\">\n                    <ul> \n");
        var0.println(" <li class=\"menuStyle\"><a href=\"../" + getTestCaseHTMLPath(var1) + "index.html\" >Index</a></li>" + "<li style=\"padding-top: 4px;\"><a href=\"" + getTestCaseHTMLPath(var1) + "ConsolidatedPage.html\" >Consolidated Page</a></li>\n");
        if(var2 == 1) {
            var0.println("\n <li class=\"menuStyle\"><a href=\"" + Directory.RUNName + var2 + Directory.SEP + "CurrentRun.html\" >" + "Run " + var2 + " </a></li>\n");
        } else {
            for(int var3 = 1; var3 <= var2; ++var3) {
                if(var3 == var2) {
                    var0.println("\n <li style=\"padding-top: 4px;padding-bottom: 4px;\"><a href=\"" + Directory.RUNName + var3 + Directory.SEP + "CurrentRun.html\" >" + "Run " + var3 + " </a></li>\n");
                    break;
                }

                var0.println("\n <li class=\"menuStyle\"><a href=\"" + Directory.RUNName + var3 + Directory.SEP + "CurrentRun.html\" >" + "Run " + var3 + " </a></li>\n");
            }
        }

        var0.println("\n                    </ul>\n                </td>\n\n");
    }

    // $FF: synthetic class
    static class SyntheticClass_1 {
        // $FF: synthetic field
        static final int[] $SwitchMap$atu$testng$reports$logging$LogAs = new int[LogAs.values().length];

        static {
            try {
                $SwitchMap$atu$testng$reports$logging$LogAs[LogAs.PASSED.ordinal()] = 1;
            } catch (NoSuchFieldError var4) {
                ;
            }

            try {
                $SwitchMap$atu$testng$reports$logging$LogAs[LogAs.FAILED.ordinal()] = 2;
            } catch (NoSuchFieldError var3) {
                ;
            }

            try {
                $SwitchMap$atu$testng$reports$logging$LogAs[LogAs.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError var2) {
                ;
            }

            try {
                $SwitchMap$atu$testng$reports$logging$LogAs[LogAs.WARNING.ordinal()] = 4;
            } catch (NoSuchFieldError var1) {
                ;
            }

        }
    }
}
