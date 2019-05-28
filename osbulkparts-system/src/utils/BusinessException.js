

export default function BusinessException({errcode, debugMessage, params, cause}) {

  this.errcode = errcode;
  this.debugMessage = debugMessage;
  this.params = params;
  this.cause = cause;

}
