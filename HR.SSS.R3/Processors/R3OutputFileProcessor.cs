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
                    EmployerNameTransformer employerNameTransformer = new EmployerNameTransformer(r3Session);
                    sw.Write(employerNameTransformer.TransformField());

                    EmployerNumberTransformer employerNumberTransformer = new EmployerNumberTransformer(r3Session);
                    sw.WriteLine(employerNumberTransformer.TransformField());

                    DateTransformer dateTransformer = new DateTransformer();
                    sw.WriteLine(dateTransformer.TransformString());
                    sw.WriteLine();

                    foreach (var r3Record in r3Session.R3Records)
                    {
                        FamilyNameTransformer familyNameTransformer = new FamilyNameTransformer(r3Session);
                        //sw.WriteLine(familyNameTransformer.TransformField(r3Record.FamilyName));
                    }
                    
                    sw.WriteLine("Add one more line ");
                    sw.WriteLine("Add one more line ");
                    sw.WriteLine("Done! ");
                }

                // Write file contents on console.     
                using (StreamReader sr = File.OpenText(outputFile))
                {
                    string s = "";
                    while ((s = sr.ReadLine()) != null)
                    {
                        Console.WriteLine(s);
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
