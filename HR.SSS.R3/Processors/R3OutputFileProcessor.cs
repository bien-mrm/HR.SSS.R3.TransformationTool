using HR.SSS.R3.Constants;
using HR.SSS.R3.Extractors.R3OutputFile.FieldTransformers;
using HR.SSS.R3.Models;
using HR.SSS.R3.Transformers.EmployeeList.FieldTransformers;
using HR.SSS.R3.Transformers.R3OutputFile.FieldTransformers;
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

                        sw.WriteLine();
                    }

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
