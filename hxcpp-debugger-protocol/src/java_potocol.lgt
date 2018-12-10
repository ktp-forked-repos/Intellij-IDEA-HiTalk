
%import debugger.IController;
%import debugger.LogtalkProtocol;
%
/**
 * This class implements the serialization and deserialization of
 * hxcpp debugger Command and Message enums.  It is meant to be compiled
 * into a .jar file, to produce Java API for serializing and deserializing
 * debugger messages to a Java OutputStream and from a Java InputStream.
 *
 * The logtalk compiler will compile this into a Java class called
 * logtalk.root.JavaProtocol.  The logtalk command that is used to do this is:
 *
 * logtalk -cp .. -java JavaProtocol -main JavaProtocol -lib debugger
 *
 * The resulting Java source files will be in JavaProtocol/src.  The only
 * relevant output is the Java source files, everything else can and should be
 * deleted.
 **/

:- protocol( java_protocol ).

  :- uses( debugger::controller ).%todo
  :- uses( debugger::logtalk_protocol ).%todo


%     Unfortunately, logtalk enums are not easily testable in Java.  So
%     use integer identifiers instead.

      :- public([
          id_error_internal/1,
          id_error_no_such_thread/1,
          id_error_no_such_file/1,
          id_error_no_such_breakpoint/1,
          id_error_bad_classname_regex/1,
          id_error_bad_clausename_regex/1,
          id_error_no_matching_clauses/1,
          id_error_bad_count/1,
          id_error_current_thread_not_stopped/1,
          id_error_evaluating_expression/1,
          id_ok/1,
          id_exited/1,
          id_detached/1,
          id_files/1,
          id_all_entities/1,
          id_entities/1,
          id_mem_bytes/1,
          id_compacted/1
      ]).

%      automates public [ enum_const/1 .. ] declaration,
%      mode declaration
%      exludes neccessity of declaring protocols/entities
%      extends/implements setp protocol with enum_set ( auto-generated )

%      :- enum( java_protocol, [public, [uses]
%          [
%                id_error_internal,
%                id_error_no_such_thread,
%                id_error_no_such_file,
%                id_error_no_such_breakpoint,
%                id_error_bad_classname_regex,
%                id_error_bad_clausename_regex,
%                id_error_no_matching_clauses,
%                id_error_bad_count,
%                id_error_current_thread_not_stopped,
%                id_error_evaluating_expression,
%                id_ok,
%                id_exited,
%                id_detached,
%                id_files,
%                id_all_entities,
%                id_entities,
%                id_mem_bytes,
%                id_compacted
%          ]).
%
          :- initialization(true).% no such??

:- end_protocol.

%    public static var IdErrorInternal : Int = 0;
%    public static var IdErrorNoSuchThread : Int = 1;
%    public static var IdErrorNoSuchFile : Int = 2;
%    public static var IdErrorNoSuchBreakpoint : Int = 3;
%    public static var IdErrorBadClassNameRegex : Int = 4;
%    public static var IdErrorBadFunctionNameRegex : Int = 5;
%    public static var IdErrorNoMatchingFunctions : Int = 6;
%    public static var IdErrorBadCount : Int = 7;
%    public static var IdErrorCurrentThreadNotStopped : Int = 8;
%    public static var IdErrorEvaluatingExpression : Int = 9;
%    public static var IdOK : Int = 10;
%    public static var IdExited : Int = 11;
%    public static var IdDetached : Int = 12;
%    public static var IdFiles : Int = 13;
%    public static var IdAllClasses : Int = 14;
%    public static var IdClasses : Int = 15;
%    public static var IdMemBytes : Int = 16;


    public static function writeServerIdentification(output : java.io.OutputStream)
    {
        LogtalkProtocol.writeServerIdentification(new OutputAdapter(output));
    }

    public static function readClientIdentification(input : java.io.InputStream)
    {
        LogtalkProtocol.readClientIdentification(new InputAdapter(input));
    }

    public static function writeCommand(output : java.io.OutputStream,
                                        command : Command)
    {
        LogtalkProtocol.writeCommand(new OutputAdapter(output), command);
    }

    public static function readMessage(input : java.io.InputStream) : Message
    {
        return LogtalkProtocol.readMessage(new InputAdapter(input));
    }

    public static function getMessageId(message : Message)
    {
        switch (message) {
        case ErrorInternal(details):
            return IdErrorInternal;
        case ErrorNoSuchThread(number):
            return IdErrorNoSuchThread;
        case ErrorNoSuchFile(fileName):
            return IdErrorNoSuchFile;
        case ErrorNoSuchBreakpoint(number):
            return IdErrorNoSuchBreakpoint;
        case ErrorBadClassNameRegex(details):
            return IdErrorBadClassNameRegex;
        case ErrorBadFunctionNameRegex(details):
            return IdErrorBadFunctionNameRegex;
        case ErrorNoMatchingFunctions(className, f, u):
            return IdErrorNoMatchingFunctions;
        case ErrorBadCount(count):
            return IdErrorBadCount;
        case ErrorCurrentThreadNotStopped(threadNumber):
            return IdErrorCurrentThreadNotStopped;
        case ErrorEvaluatingExpression(details):
            return IdErrorEvaluatingExpression;
        case OK:
            return IdOK;
        case Exited:
            return IdExited;
        case Detached:
            return IdDetached;
        case Files(list):
            return IdFiles;
        case AllClasses(list):
            return IdAllClasses;
        case Classes(list):
            return IdClasses;
        case MemBytes(bytes):
            return IdMemBytes;
        case Compacted(bytesBefore, a):
            return IdCompacted;
        case Collected(bytesBefore, a):
            return IdCollected;
        case ThreadLocation(number, s, c, f, fi, l):
            return IdThreadLocation;
        case FileLineBreakpointNumber(number):
            return IdFileLineBreakpointNumber;
        case ClassFunctionBreakpointNumber(number, u):
            return IdClassFunctionBreakpointNumber;
        case Breakpoints(list):
            return IdBreakpoints;
        case BreakpointDescription(number, l):
            return IdBreakpointDescription;
        case BreakpointStatuses(list):
            return IdBreakpointStatuses;
        case ThreadsWhere(list):
            return IdThreadsWhere;
        case Variables(list):
            return IdVariables;
        case Structured(structuredValue):
            return IdStructured;
        case Value(expression, t, v):
            return IdValue;
        case ThreadCreated(number):
            return IdThreadCreated;
        case ThreadTerminated(number):
            return IdThreadTerminated;
        case ThreadStarted(number):
            return IdThreadStarted;
        case ThreadStopped(number, s, c, f, fi, l):
            return IdThreadStopped;
        }
    }

    public static function commandToString(command : Command) : String
    {
        return Std.string(command);
    }

    public static function messageToString(message : Message) : String
    {
        return Std.string(message);
    }

    public static function main()
    {
        var stdout = untyped __java__('System.out');
        LogtalkProtocol.writeMessage(new OutputAdapter(stdout),
                                  Message.ThreadsWhere
                     (Where(0, Running, 
                            Frame(true, 0, "h", "i", "p",
                                  10, Terminator),
                            Terminator)));
        Sys.stderr().writeString("Reading message\n");
        var msg = readMessage(untyped __java__('System.in'));
        Sys.stderr().writeString("Read message\n");
        Sys.stderr().writeString("Message is: " + msg + "\n");
    }
}


private class OutputAdapter extends logtalk.io.Output
{
    public function new(os : java.io.OutputStream)
    {
        mOs = os;
    }

    public override function writeBytes(bytes : logtalk.io.Bytes, pos : Int,
                                        len : Int) : Int
    {
        try {
            mOs.write(bytes.getData(), pos, len);
            return len;
        }
        catch (e : java.io.IOException) {
            throw "IOException: " + Std.string(e);
        }
    }

    private var mOs : java.io.OutputStream;
}


private class InputAdapter extends logtalk.io.Input
{
    public function new(is : java.io.InputStream)
    {
        mIs = is;
    }

    public override function readBytes(bytes : logtalk.io.Bytes, pos : Int,
                                       len : Int) : Int
    {
        try {
            return mIs.read(bytes.getData(), pos, len);
        }
        catch (e : java.io.IOException) {
            throw "IOException: " + Std.string(e);
        }
    }

    private var mIs : java.io.InputStream;
}
