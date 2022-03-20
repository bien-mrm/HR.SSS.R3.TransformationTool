using HR.SSS.R3.Constants;
using HR.SSS.R3.Extractors.R3OutputFile.FieldTransformers;
using HR.SSS.R3.Models;
using HR.SSS.R3.Transformers.EmployeeList.FieldTransformers;
using HR.SSS.R3.Transformers.R3OutputFile.FieldTransformers;
using HR.SSS.R3.Transformers.R3OutputFile.StringTransformers;
using System;
using System.IO;

namespace HR.SSS.R3.Extractors
{
    public static class R3OutputFileProcessor
    {
        public static void CreateOutputFile(R3SessionContainer r3Session)
        {
            string outputFolder = $"{ r3Session.InputDirectory }\\{ OutputTypeConstants.R3OutputFile }";
            string outputFile = $"{ outputFolder }\\{ r3Session.OutputFileName }";

            try
            {
                // Create R3 Output folder
                if (!Directory.Exists(outputFolder))
                {
                    Directory.CreateDirectory(outputFolder);
                }

                // Check if file already exists. If yes, delete it.     
                if (File.Exists(outputFile))
                {
                    File.Delete(outputFile);
                }

                // Create a new file
                using (StreamWriter sw = new StreamWriter(outputFile))
                {
                    sw.Write(R3OutputFileConstants.Numbers.LinePrepend00);
                    ELEmployerNameTransformer employerNameTransformer = new ELEmployerNameTransformer(r3Session.EmployerName);
                    sw.Write(employerNameTransformer.TransformField());

                    TransactionCodeTransformer transactionCodeTransformer = new TransactionCodeTransformer(r3Session);
                    sw.WriteLine(transactionCodeTransformer.TransformField());

                    foreach (var r3Record in r3Session.R3Records)
                    {
                        sw.Write(R3OutputFileConstants.Numbers.LinePrepend20);
                        R3FamilyNameTransformer r3FamilyNameTransformer = new R3FamilyNameTransformer(r3Record.FamilyName);
                        sw.Write(r3FamilyNameTransformer.TransformField());

                        R3GivenNameTransformer r3GivenNameTransformer = new R3GivenNameTransformer(r3Record.GivenName);
                        sw.Write(r3GivenNameTransformer.TransformField());

                        R3MiddleInitialTransformer r3MiddleInitialTransformer = new R3MiddleInitialTransformer(r3Record.MiddleInitial);
                        sw.Write(r3MiddleInitialTransformer.TransformField());

                        R3SssNumberTransformer r3SssNumberTransformer = new R3SssNumberTransformer(r3Record.SssNumber);
                        sw.Write(r3SssNumberTransformer.TransformField());

                        R3AmountTransformer r3AmountTransformer = new R3AmountTransformer();
                        sw.Write(r3AmountTransformer.TransformString(4));
                        sw.Write(r3AmountTransformer.TransformString(1));

                        ELSssContributionTransformer eLSssContributionTransformer = new ELSssContributionTransformer(r3Record.SssContribution);
                        sw.Write(eLSssContributionTransformer.TransformField());

                        sw.Write(r3AmountTransformer.TransformString(2));
                        sw.Write(r3AmountTransformer.TransformString(2));
                        sw.Write(r3AmountTransformer.TransformString(2));
                        sw.Write(r3AmountTransformer.TransformString(2));
                        sw.Write(r3AmountTransformer.TransformString(2));

                        R3NormalTransformer r3NormalTransformer = new R3NormalTransformer();
                        sw.Write(r3NormalTransformer.TransformString());

                        sw.WriteLine();
                    }

                    R3TotalTransformer r3TotalTransformer = new R3TotalTransformer(r3Session);
                    sw.WriteLine(r3TotalTransformer.TransformField());

                    sw.Close();
                }
            }
            catch (Exception Ex)
            {
                Console.WriteLine(Ex.ToString());
            }
        }
    }
}
