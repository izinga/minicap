#ifndef MINICAP_SIMPLE_SERVER_HPP
#define MINICAP_SIMPLE_SERVER_HPP

class SimpleServer {
public:
  SimpleServer();
  ~SimpleServer();

  int
  check(const char* sockname);
  int
  start(const char* sockname);

  int accept();

private:
  int mFd;
};

#endif
