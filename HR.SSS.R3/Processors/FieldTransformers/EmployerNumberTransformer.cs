using HR.SSS.R3.Models;
using HR.SSS.R3.Processors.Interfaces;
using HR.SSS.R3.Utilities;

namespace HR.SSS.R3.Processors.FieldTransformers
{
    public class EmployerNumberTransformer : IFieldTransformer
    {
        public string TransformField(R3SessionContainer r3Session)
        {
            // [R30395218183122021.03161453] is a combination of characters

            var random = Randomizer.RandomString(8);
            string code = $"[R3{ r3Session.EmployerNumber + r3Session.ApplicablePeriod }.{ random }]";

            return code;
        }
    }
}
