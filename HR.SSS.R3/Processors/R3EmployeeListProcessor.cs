using HR.SSS.R3.Models;
using HR.SSS.R3.Transformers.EmployeeList.FieldTransformers;
using HR.SSS.R3.Transformers.EmployeeList.StringTransformers;
using System;
using System.IO;
using HR.SSS.R3.Constants;

namespace HR.SSS.R3.Extractors
{
    public static class R3EmployeeListProcessor
    {
        public static void CreateOutputFile(R3SessionContainer r3Session)
        {
            string outputFolder = $"{ r3Session.InputDirectory }\\{ OutputTypeConstants.R3EmployeeList }";
            string outputFile = $"{ outputFolder }\\{ r3Session.OutputFileName }";

            try
            {
                // R3 Output folder
                if (!Directory.Exists(outputFolder))
                {
                    Directory.CreateDirectory(outputFolder);
                }

                // Create a new file     
                using (StreamWriter sw = new StreamWriter(outputFile))
                {
                    ELEmployerNameTransformer employerNameTransformer = new ELEmployerNameTransformer(r3Session.EmployerName);
                    sw.Write(employerNameTransformer.TransformField());

                    ELEmployerNumberTransformer employerNumberTransformer = new ELEmployerNumberTransformer(r3Session.OutputFileName);
                    sw.WriteLine(employerNumberTransformer.TransformField());

                    ELDateTransformer dateTransformer = new ELDateTransformer();
                    sw.WriteLine(dateTransformer.TransformString());
                    sw.WriteLine();

                    ELHeaderTransformer headerTransformer = new ELHeaderTransformer();
                    sw.WriteLine(headerTransformer.TransformString());
                    sw.WriteLine();

                    foreach (var r3Record in r3Session.R3Records)
                    {
                        ELFamilyNameTransformer familyNameTransformer = new ELFamilyNameTransformer(r3Record.FamilyName);
                        sw.Write(familyNameTransformer.TransformField());

                        ELGivenNameTransformer givenNameTransformer = new ELGivenNameTransformer(r3Record.GivenName);
                        sw.Write(givenNameTransformer.TransformField());

                        ELMiddleInitialTransformer middleInitialTransformer = new ELMiddleInitialTransformer(r3Record.MiddleInitial);
                        sw.Write(middleInitialTransformer.TransformField());

                        ELSssNumberTransformer sssNumberTransformer = new ELSssNumberTransformer(r3Record.SssNumber);
                        sw.Write(sssNumberTransformer.TransformField());

                        ELSssContributionTransformer sssContributionTransformer = new ELSssContributionTransformer(r3Record.SssContribution);
                        sw.Write(sssContributionTransformer.TransformField());

                        ELEcAmountTransformer ecAmountTransformer = new ELEcAmountTransformer(r3Record.EcAmount);
                        sw.Write(ecAmountTransformer.TransformField());

                        ELRemarkTransformer remarkTransformer = new ELRemarkTransformer(r3Record.Remark);
                        sw.Write(remarkTransformer.TransformField());

                        ELDateHiredTransformer dateHiredTransformer = new ELDateHiredTransformer(r3Record.DateHired);
                        sw.Write(dateHiredTransformer.TransformField());

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
