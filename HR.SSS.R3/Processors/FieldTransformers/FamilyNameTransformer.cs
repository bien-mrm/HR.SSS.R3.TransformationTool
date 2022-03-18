using HR.SSS.R3.Models;
using HR.SSS.R3.Processors.Abstracts;
using HR.SSS.R3.Processors.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HR.SSS.R3.Processors.FieldTransformers
{
    class FamilyNameTransformer : FieldTransformer, IFieldTransformable
    {
        private readonly R3SessionContainer _r3Session;

        public FamilyNameTransformer(R3SessionContainer r3Session) : base(r3Session)
        {
            this._r3Session = r3Session;
        }

        public string TransformField()
        {
            // Convert to uppercase
        }
    }
}
