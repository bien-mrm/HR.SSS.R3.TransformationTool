using HR.SSS.R3.Transformers.Interfaces;

namespace HR.SSS.R3.Transformers.EmployeeList.StringTransformers
{
    public class ELHeaderTransformer : IStringTransformable
    {
        public string TransformString()
        {
            return "FAMILY NAME     GIVEN NAME      MI  SS NUMBER       S.S.    E.C.   RMRK  DTHRD";
        }
    }
}
