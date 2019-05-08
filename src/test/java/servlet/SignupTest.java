/**
 * 
 */
package servlet;

//public class SignupTest {
//    private static HttpServletRequest req;
//    private static HttpServletResponse resp;
//
//    @Before
//    public void setUp() throws Exception {
//        req = Mockito.mock(HttpServletRequest.class);
//        resp = Mockito.mock(HttpServletResponse.class);
//        Mockito.mock(HttpServletRequest.class);
//        Answer<String> ans = new Answer<>() {
//            @Override
//            public String answer(InvocationOnMock invocation) throws Throwable {
//                String ans = null;
//                switch ((String) invocation.getArgument(0)) {
//                case "login":
//                    ans = "User1";
//                    break;
//                case "password":
//                case "passwordConfirmation":
//                    ans = "12345678";
//                }
//                return ans;
//            }
//
//        };
//    }
//
//    @BeforeClass
//    public static void initializeTestDatabase() {
//        ConnectionSource.connectToDatabase("testdb");
//    }
//
//    @AfterClass
//    public static void initializeDefaultDatabase() {
//        ConnectionSource.connectToDefaultDatabase();
//    }
//
//    @After
//    public void truncateDatabase() {
//        try {
//            UsersTable.getInstance().executeSystemUpdate("TRUNCATE TABLE USERS");
//            UsersTable.getInstance().executeSystemQuery("SELECT setval('users_id_seq', 1)");
//        } catch (ActiveRecordException e) {
//            System.out.println("Error while truncate Query: " + e.getMessage());
//        }
//    }
//
//    @Test
//    public void registratingValidUser() throws Exception {
//        Answer<String> ans = new Answer<>() {
//            @Override
//            public String answer(InvocationOnMock invocation) throws Throwable {
//                String ans = null;
//                switch ((String) invocation.getArgument(0)) {
//                case "login":
//                    ans = "User1";
//                    break;
//                case "password":
//                case "passwordConfirmation":
//                    ans = "12345678";
//                }
//                return ans;
//            }
//
//        };
//        req = Mockito.mock(HttpServletRequest.class, ans);
//        SignupServlet serv = new SignupServlet();
//        serv.service(req, resp);
//        List<String> violations = (List<String>) req.getAttribute("violations");
//        assertTrue(violations.isEmpty());
//        assertTrue(violations.size() == 2);
//    }
//}
