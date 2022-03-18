using HR.SSS.R3.Models;
using HR.SSS.R3.Processors.FieldTransformers;
using HR.SSS.R3.Processors.StringTransformers;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HR.SSS.R3.Processors
{
    public static class R3OutputFileProcessor
    {
        public static void CreateOutputFile(R3SessionContainer r3Session)
        {
            string outputFile = $"{ r3Session.InputDirectory }\\{ r3Session.OutputFileName }";

            try
            {
                // Check if file already exists. If yes, delete it.     
                if (File.Exists(outputFile))
                {
                    File.Delete(outputFile);
                }

                // Create a new file     
                using (StreamWriter sw = File.CreateText(outputFile))
                {
                    EmployerNameTransformer employerNameTransformer = new EmployerNameTransformer(r3Session.EmployerName);
                    sw.Write(employerNameTransformer.TransformField());

                    EmployerNumberTransformer employerNumberTransformer = new EmployerNumberTransformer(r3Session);
                    sw.WriteLine(employerNumberTransformer.TransformField());

                    DateTransformer dateTransformer = new DateTransformer();
                    sw.WriteLine(dateTransformer.TransformString());
                    sw.WriteLine();

                    HeaderTransformer headerTransformer = new HeaderTransformer();
                    sw.WriteLine(headerTransformer.TransformString());
                    sw.WriteLine();

                    foreach (var r3Record in r3Session.R3Records)
                    {
                        FamilyNameTransformer familyNameTransformer = new FamilyNameTransformer(r3Record.FamilyName);
                        sw.Write(familyNameTransformer.TransformField());

                        GivenNameTransformer givenNameTransformer = new GivenNameTransformer(r3Record.GivenName);
                        sw.Write(givenNameTransformer.TransformField());

                        MiddleInitialTransformer middleInitialTransformer = new MiddleInitialTransformer(r3Record.MiddleInitial);
                        sw.Write(middleInitialTransformer.TransformField());

                        SssNumberTransformer sssNumberTransformer = new SssNumberTransformer(r3Record.SssNumber);
                        sw.Write(sssNumberTransformer.TransformField());

                        SssContributionTransformer sssContributionTransformer = new SssContributionTransformer(r3Record.SssContribution);
                        sw.Write(sssContributionTransformer.TransformField());

                        EcAmountTransformer ecAmountTransformer = new EcAmountTransformer(r3Record.EcAmount);
                        sw.Write(ecAmountTransformer.TransformField());

                        RemarkTransformer remarkTransformer = new RemarkTransformer(r3Record.Remark);
                        sw.Write(remarkTransformer.TransformField());

                        DateHiredTransformer dateHiredTransformer = new DateHiredTransformer(r3Record.DateHired);
                        sw.Write(dateHiredTransformer.TransformField());

                        sw.WriteLine();
                    }
                }
            }
            catch (Exception Ex)
            {
                Console.WriteLine(Ex.ToString());
            }
        }
    }
}
