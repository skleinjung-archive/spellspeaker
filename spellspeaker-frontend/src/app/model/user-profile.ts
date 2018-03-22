export class UserProfile {
  username: string;

  // methods for serialization/deserialization to/from local storage

  loadFrom(source: any): UserProfile {
    this.username = source['username'];
    return this;
  }
}
