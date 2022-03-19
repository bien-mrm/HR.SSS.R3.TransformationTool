using HR.SSS.R3.Extractors.Interfaces;

namespace HR.SSS.R3.Extractors.EmployeeList.StringTransformers
{
    public class HeaderTransformer : IStringTransformable
    {
        public string TransformString()
        {
            return "FAMILY NAME     GIVEN NAME      MI  SS NUMBER       S.S.    E.C.   RMRK  DTHRD";
        }
    }
}
