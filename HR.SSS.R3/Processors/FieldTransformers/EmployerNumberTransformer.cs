using HR.SSS.R3.Models;
using HR.SSS.R3.Processors.Abstracts;
using HR.SSS.R3.Processors.Interfaces;
using HR.SSS.R3.Utilities;

namespace HR.SSS.R3.Processors.FieldTransformers
{
    public class EmployerNumberTransformer : FieldTransformer, IFieldTransformable
    {
        private readonly R3SessionContainer _r3Session;

        public EmployerNumberTransformer(R3SessionContainer r3Session) : base(r3Session)
        {
            this._r3Session = r3Session;
        }

        public string TransformField()
        {
            // [R30395218183122021.03161453] is a combination of characters

            var random = Randomizer.RandomString(8);
            string code = $"[R3{ this._r3Session.EmployerNumber + this._r3Session.ApplicablePeriod }.{ random }]";

            return code;
        }
    }
}
