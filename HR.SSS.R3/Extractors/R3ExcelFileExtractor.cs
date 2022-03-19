using HR.SSS.R3.Constants;
using HR.SSS.R3.Models;
using OfficeOpenXml;
using System;
using System.IO;
using System.Windows;

namespace HR.SSS.R3.Extractors
{
    public static class R3ExcelFileExtractor
    {
        public static void CaptureExcelRecords(R3SessionContainer r3Session)
        {
            try
            {
                // Read the Excel File
                FileInfo fileInfo = new FileInfo(r3Session.InputFile);
                ExcelPackage.LicenseContext = LicenseContext.Commercial;

                using (var ep = new ExcelPackage(fileInfo))
                {
                    ExcelWorksheet worksheet = ep.Workbook.Worksheets[r3Session.InputSheetName];

                    if (worksheet == null)
                    {
                        // Prompt user to check worksheet
                        string title = "Missing Moderators Worksheet";
                        string message = $"Please ensure that a worksheet named { r3Session.InputSheetName } exists in the Excel file.";
                        MessageBox.Show(message, title, MessageBoxButton.OK, MessageBoxImage.Warning);
                        return;
                    }

                    // Get rows details
                    int rows = worksheet.Dimension.Rows;
                    int startingRow = r3Session.IsHeaderPresent ? 2 : 1;

                    for (int r = startingRow; r <= rows; r++)
                    {
                        var familyName = worksheet.Cells[r, (int) ExcelFileColumn.FamilyName].Value?.ToString();
                        var givenName = worksheet.Cells[r, (int) ExcelFileColumn.GivenName].Value?.ToString();
                        var middleInitial = worksheet.Cells[r, (int) ExcelFileColumn.MiddleInitial].Value?.ToString();
                        var sssNumber = worksheet.Cells[r, (int) ExcelFileColumn.SssNumber].Value?.ToString();
                        var sssContribution = worksheet.Cells[r, (int) ExcelFileColumn.SssContribution].Value?.ToString();
                        var ecAmount = worksheet.Cells[r, (int) ExcelFileColumn.EcAmount].Value?.ToString();
                        var remark = worksheet.Cells[r, (int) ExcelFileColumn.Remark].Value?.ToString();
                        var dateHired = worksheet.Cells[r, (int) ExcelFileColumn.DateHired].Value?.ToString();

                        r3Session.R3Records.Add(new R3Record()
                        {
                            FamilyName = familyName,
                            GivenName = givenName,
                            MiddleInitial = middleInitial,
                            SssNumber = sssNumber,
                            SssContribution = sssContribution,
                            EcAmount = ecAmount,
                            Remark = remark,
                            DateHired = dateHired
                        });

                        // Incrementally add all SSS Contributions to a total amount
                        r3Session.TotalAmount += Convert.ToDouble(sssContribution);
                    }
                }
            }
            catch (InvalidOperationException ioe)
            {
                // Prompt user to close Excel editor
                string title = "File Used by Another Process";
                string message = "Please close any editor that is currently reading your Excel file (e.g. Microsoft Excel) or make sure that it is not set as Read-Only then click the submit button again.";
                MessageBox.Show(message, title, MessageBoxButton.OK, MessageBoxImage.Warning);
            }
        }
    }
}